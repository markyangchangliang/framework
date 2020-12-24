package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markyang.framework.app.base.annotation.ApplyPostGetHook;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.form.search.WorkerSearchForm;
import com.markyang.framework.app.system.service.*;
import com.markyang.framework.app.system.enumeration.DeptWorkerEnum;
import com.markyang.framework.app.system.enumeration.UserEnum;
import com.markyang.framework.app.system.repository.OrgRepository;
import com.markyang.framework.app.system.repository.UserRoleRepository;
import com.markyang.framework.app.system.repository.WorkerRepository;
import com.markyang.framework.pojo.entity.system.*;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DeptWorkerTreeDto;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.service.common.CommonInfoCacheService;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.BuilderUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 职员管理(Worker)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class WorkerServiceImpl extends AbstractSearchableServiceImpl<Worker, WorkerRepository, WorkerSearchForm> implements WorkerService {

    private final OrgRepository orgRepository;
    private final WorkerRepository repository;
    private final DeptService deptService;
    private final DeptWorkerService deptWorkerService;
    private final UserService userService;
    private final UserRoleRepository userRoleRepository;
    private final SystemOptionService systemOptionService;
    private final CommonInfoCacheService commonInfoCacheService;


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Worker create() {
        return Worker.builder().build();
    }


    @Override
    public void beforeAdd(Worker entity) {

        if (StringUtils.isAnyBlank(entity.getOrgId(), entity.getDeptId())) {
            throw new UpdateDeniedException("机构编号[orgId]或者部门编号[deptId]不能为空!");
        }
        if (StringUtils.isNotBlank(entity.getMobilePhone())) {
            // 判断用户是否存在
            Worker worker = this.repository.getWorkerByPhone(entity.getMobilePhone());
            // 先判断用户的离职状态,如果在职,不允许添加
            if (Objects.isNull(worker)) {
                throw new UpdateDeniedException("手机号码[" + entity.getMobilePhone() + "]已经被[" + entity.getOrgName() + " - "
                        + entity.getDeptName() + " - " + entity.getName() + "]占用！");
            }
        }
    }


    @Override
    public void afterAdd(Worker entity) {
        // 插入职员部门关系数据
        DeptWorker deptWorker = DeptWorker.builder().deptId(entity.getDeptId()).workerId(entity.getId())
                .status(DeptWorkerEnum.STATUS_ENABLED.getValue()).build();

        deptWorker = deptWorkerService.add(deptWorker);
        if (deptWorker.getId() == null) {
            throw new UpdateDeniedException("添加职员失败！");
        }

        String userName;
        if (StringUtils.isNotBlank(entity.getOrgUserId())) {
            userName = entity.getOrgUserId();
        } else if (StringUtils.isNotBlank(entity.getMobilePhone())) {
            userName = entity.getMobilePhone();
        } else {
            userName = entity.getName();
        }

        String passwordSrc = StringUtils.isNotBlank(entity.getMobilePhone()) ? entity.getMobilePhone() : "123456";

        // 添加用户
        User user = User.builder().workerId(entity.getId()).username(userName).password(passwordSrc)
                .status(UserEnum.STATUS_ENABLED.getValue()).build();
        User userAdd = this.userService.add(user);
        if (Objects.isNull(userAdd)) {
            throw new UpdateDeniedException("保存用户信息失败！");
        }

        // 添加默认角色
        try {
            // 查询默认角色
            Org org = this.orgRepository.selectById(entity.getOrgId());
            if (Objects.isNull(org)) {
                throw new UpdateDeniedException("没有找到职员对应的机构与部门信息！");
            }

            String type = "默认用户角色-" + org.getType();
            // 若果有默认角色，则授权
            SystemOption systemOption = this.systemOptionService.getOne(Wrappers.<SystemOption>lambdaQuery().eq(SystemOption::getId, type));
            if (Objects.nonNull(systemOption)) {
                String[] defaultUserRoles = StringUtils.split(systemOption.getValue(), FrameworkConstants.COMMA_SEPARATOR);
                if (ArrayUtils.isNotEmpty(defaultUserRoles)) {
                    this.userRoleRepository.insertBatchSomeColumn(
                            Arrays.stream(defaultUserRoles)
                                    .parallel()
                                    .map(roleId -> UserRole.builder().userId(userAdd.getId()).roleId(roleId).build())
                                    .collect(Collectors.toList())
                    );
                }
            }

        } catch (Exception ex) {
            System.out.println("添加默认角色失败：" + ex.getMessage());
        }

        // 先保存企业微信用户信息
        // TODO 添加到企业微信

        // 更新缓存信息
        this.commonInfoCacheService.updateCachedWorkerInfo(entity.getId());
    }


    @Override
    public void afterUpdate(Worker entity) {
        // 查找旧的信息
//        this.repository.findById(entity.getId()).ifPresent(worker -> {
//            // 检测职员部门是否有变化
//            if (!Objects.isNull(entity.getDeptId()) && !entity.getDeptId().equals(worker.getDeptId())) {
//                // 先停用旧部门
//                List<DeptWorker> deptWorkerList =
//                        this.deptWorkerService.findByDeptIdAndWorkerIdAndStatus(worker.getDeptId(), entity.getId(), "a");
//                if (!CollectionUtils.isEmpty(deptWorkerList)) {
//                    DeptWorker deptWorkerDO = deptWorkerList.get(0);
//                    deptWorkerDO.setStatus(DeptWorkerEnum.STATUS_DISABLED.getValue());
//                    DeptWorker deptWorkerUpdate = this.deptWorkerService.update(deptWorkerDO);
//                    if (Objects.isNull(deptWorkerUpdate)) {
//                        throw new UpdateDeniedException("修改旧部门信息失败！");
//                    }
//                }
//
//                // 再插入新部门
//                DeptWorker deptWorker = DeptWorker.builder().deptId(entity.getDeptId()).workerId(entity.getId())
//                        .status(DeptWorkerEnum.STATUS_ENABLED.getValue()).build();
//                DeptWorker deptWorkerAdd = this.deptWorkerService.add(deptWorker);
//                if (Objects.isNull(deptWorkerAdd.getId())) {
//                    throw new UpdateDeniedException("保存新部门信息失败！");
//                }
//            }
//
//            // 修改企业微信用户信息
//            // TODO 添加到企业微信
//        });
        // 更新缓存信息
        this.commonInfoCacheService.updateCachedWorkerInfo(entity.getId());
    }


    @Override
    public void afterDelete(Worker entity) {
        // 删除和部门关联关系
        this.deptWorkerService.deleteByWorkerId(entity.getId());
        // 删除用户
        this.userService.getByWorkerId(entity.getId()).ifPresent(user -> this.userService.deleteById(user.getId()));
        // 删除企业微信用户信息
        // TODO 添加到企业微信

        // 更新缓存信息
        this.commonInfoCacheService.deleteCachedUserWorkerInfo(null, entity.getId());
    }


    /**
     * 获取树形结构
     *
     * @param orgId         机构编号
     * @param type          类型
     * @param positional    职称
     * @param positionGrade 岗位等级
     * @param post          职务
     * @param online        是否支持网上项目
     * @return 树形结构
     */
    @Override
    public List<TreeNode> getDeptWorkerTree(String orgId, String type, String positional, String post,
                                            String positionGrade, String online) {
        List<DeptWorkerTreeDto> deptWorkerList = this.repository.getDeptWorkerTreeData(orgId, type, positional, post, positionGrade, online);
        return BuilderUtils.newTreeBuilder(deptWorkerList).topLevelId("0").build();
    }

    /**
     * 获取某个父节点下面的所有子节点
     *
     * @param deptDOList
     * @param pid
     * @param deptTreeList
     * @return
     */
    public static List<Dept> getDeptTreeList(List<Dept> deptDOList, String pid, List<Dept> deptTreeList) {
        for (Dept dept : deptDOList) {
            // 遍历出父id等于参数的id，add进子节点集合
            if (pid.equals(dept.getParentId())) {
                // 递归遍历下一级
                getDeptTreeList(deptDOList, dept.getId(), deptTreeList);
                deptTreeList.add(dept);
            }
        }
        return deptTreeList;
    }

    /**
     * 获取职员部门信息
     *
     * @param workerId 职员编号
     * @return 部门信息
     */
    @Override
    public DeptWorker getWorkerDept(String workerId) {
        List<DeptWorker> deptWorkerList = this.deptWorkerService.list(Wrappers.<DeptWorker>lambdaQuery().eq(DeptWorker::getWorkerId, workerId));

        if (CollectionUtils.isNotEmpty(deptWorkerList)) {
            return deptWorkerList.get(0);
        } else {
            return null;
        }
    }


    /**
     * 职员转科
     *
     * @param deptWorker 职员部门信息
     */
    @Override
    public void updateWorkerDept(DeptWorker deptWorker) {
        List<DeptWorker> deptWorkerList = this.deptWorkerService.list(Wrappers.<DeptWorker>lambdaQuery().eq(DeptWorker::getWorkerId, deptWorker.getWorkerId()).eq(DeptWorker::getStatus, DeptWorkerEnum.STATUS_ENABLED.getValue()));
        if (CollectionUtils.isNotEmpty(deptWorkerList)) {
            DeptWorker deptWorkerSrc = deptWorkerList.get(0);
            if (Objects.equals(deptWorkerSrc.getDeptId(), deptWorker.getDeptId())) {
                throw new UpdateDeniedException("新旧科室相同！");
            }
            for (DeptWorker deptWorkerDest : deptWorkerList) {
                deptWorkerDest.setStatus(DeptWorkerEnum.STATUS_DISABLED.getValue());
                this.deptWorkerService.update(deptWorkerDest);
            }

            deptWorker.setStatus(DeptWorkerEnum.STATUS_ENABLED.getValue());
            deptWorker.setId(null);
            DeptWorker deptWorkerAdd = this.deptWorkerService.add(deptWorker);
            if (Objects.isNull(deptWorkerAdd)) {
                throw new UpdateDeniedException("换科失败！");
            }
        } else {
            throw new UpdateDeniedException("未获取到职员部门信息！");
        }
    }

    /**
     * 获取机构部门职员
     *
     * @param deptId 部门ID
     * @param page   分页对象
     * @return 结果对象
     */
    @ApplyPostGetHook
    @Override
    public PageVo<Worker> getOrgDeptWorkers(String deptId, Page<Worker> page) {
        return PageVo.of(this.repository.getOrgDeptWorkers(page, deptId));
    }

    /**
     * 获取推荐专家
     *
     * @param orgId 机构编号
     * @param page  分页对象
     * @return 结果对象
     */
    @ApplyPostGetHook
    @Override
    public PageVo<WorkerInfoDto> getOnlineWorkers(String orgId, String deptId, String deptType, String positional, String workerName, Page<WorkerInfoDto> page) {
        return PageVo.of(this.repository.getOnlineWorkers(page, orgId, deptId, deptType, positional, workerName));
    }

    /**
     * 获取推荐专家详情
     * @param workerId 职员编号
     * @return 结果对象
     */
    @ApplyPostGetHook
    @Override
    public WorkerInfoDto getOnlineWorkerInfo(String workerId) {
        return this.repository.getOnlineWorkerInfo(workerId);
    }

}