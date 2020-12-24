package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.OrgSearchForm;
import com.markyang.framework.app.system.service.DeptService;
import com.markyang.framework.app.system.service.DeptWorkerService;
import com.markyang.framework.app.system.service.OrgService;
import com.markyang.framework.app.system.service.WorkerService;
import com.markyang.framework.app.system.enumeration.DeptEnum;
import com.markyang.framework.app.system.enumeration.WorkerEnum;
import com.markyang.framework.app.system.repository.OrgRepository;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.OrgTreeDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.pojo.entity.system.Worker;
import com.markyang.framework.pojo.enumeration.system.GenderEnum;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.BeanOperationUtils;
import com.markyang.framework.util.BuilderUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 机构管理(Org)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 14:52:12
 */
@AllArgsConstructor
@Service
public class OrgServiceImpl extends AbstractSearchableServiceImpl<Org, OrgRepository, OrgSearchForm> implements OrgService {

    private final DeptService deptService;
    private final WorkerService workerService;
    private final DeptWorkerService deptWorkerService;
    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Org create() {
        return Org.builder().build();
    }

    @Override
    public void beforeAdd(Org entity) {
        List<Org> orgList = this.list(Wrappers.<Org>lambdaQuery().eq(Org::getCode, entity.getCode()));
        if (orgList != null && orgList.size() > 0) {
            throw new UpdateDeniedException("机构代码已存在！");
        }
    }

    @Override
    public void afterAdd(Org entity) {
        // 添加默认的部门
        Dept dept = Dept.builder()
                .parentId("0")
                .orgId(entity.getId())
                .name(entity.getName())
                .type(DeptEnum.TYPE_ADMINISTRATIVE.getValue())
                .build();
        Dept defaultDept = this.deptService.addDefaultDept(dept);
        if (Objects.isNull(defaultDept)) {
            throw new UpdateDeniedException("添加默认部门失败！");
        }
        // 添加默认职员
        Worker worker = Worker.builder()
                .type(WorkerEnum.TYPE_OFFICIALS.getValue())
                .orgId(entity.getId())
                .deptId(defaultDept.getId())
                .gender(GenderEnum.MALE.getValue())
                .name(entity.getName())
                .mobilePhone(entity.getPhone())
                .diagnoseScore(new BigDecimal(0))
                .diagnoseSpeed(new BigDecimal(0))
                .status(WorkerEnum.STATUS_ENABLED.getValue())
                .online(WorkerEnum.ONLINE_DISABLED.getValue())
                .recommend(WorkerEnum.RECOMMEND_DISABLED.getValue())
                .build();
        Worker defaultWorker = this.workerService.add(worker);
        if (Objects.isNull(defaultWorker)) {
            throw new UpdateDeniedException("添加默认职员失败！");
        }
        // TODO 添加企业微信
        this.commonInfoCacheService.updateCachedOrgInfo(entity);
    }

    @Override
    public void afterUpdate(Org entity) {
        // TODO 修改企业微信
        this.commonInfoCacheService.updateCachedOrgInfo(entity);
    }

    @Override
    public void beforeDelete(Org entity) {
        List<Org> orgList = this.list(Wrappers.<Org>lambdaQuery().eq(Org::getParentId, entity.getParentId()));
        if (!CollectionUtils.isEmpty(orgList)) {
            throw new UpdateDeniedException("该机构下已经增加了部门信息，不能删除！");
        }
        List<Dept> deptList = deptService.getListOfOrg(entity.getId());
        if (!CollectionUtils.isEmpty(deptList)) {
            throw new UpdateDeniedException("该机构下有子机构信息，不能删除！");
        }
    }

    @Override
    public void afterDelete(Org entity) {
        // TODO 删除企业微信信息
        this.commonInfoCacheService.deleteCachedOrgInfo(entity.getId());
    }

    /**
     * 按照编号获取属性菜单
     *
     * @param parentId 父级编号
     * @return 树形结构
     */
    @Override
    public ResultVo<List<TreeNode>> getOrgTree(String parentId) {
        if (Objects.isNull(parentId)) {
            parentId = "0";
        }
        List<Org> orgList = this.getList();
        List<OrgTreeDto> orgTreeDtoList = orgList.parallelStream()
                .map(org -> BeanOperationUtils.fromBean(OrgTreeDto.class, org)).collect(Collectors.toList());
        List<TreeNode> treeNodes = BuilderUtils.newTreeBuilder(orgTreeDtoList).topLevelId(parentId).build();
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, treeNodes);
    }

}