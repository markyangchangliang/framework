package com.markyang.framework.app.system.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markyang.framework.pojo.dto.system.DeptWorkerTreeDto;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.entity.system.Worker;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职员管理(Worker)仓库类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:16:18
 */
public interface WorkerRepository extends FrameworkRepository<Worker> {

    /**
     * 根据手机号用户职员
     *
     * @param phone 手机号
     * @return 职员对象
     */
    Worker getWorkerByPhone(@Param("phone") String phone);

    /**
     * 根据ID获取职员
     *
     * @param id 主键ID
     * @return 职员对象
     */
    Worker getWorkerById(@Param("id") String id);

    /**
     * 获取部门职员数据
     *
     * @param orgId         机构ID
     * @param type          类型
     * @param positional    职位
     * @param post          职务
     * @param positionGrade 岗位等级
     * @param online        网上
     * @return 数据
     */
    List<DeptWorkerTreeDto> getDeptWorkerTreeData(@Param("orgId") String orgId, @Param("type") String type,
                                                  @Param("positional") String positional, @Param("post") String post,
                                                  @Param("positionGrade") String positionGrade, @Param("online") String online);


    /**
     * 查询职员
     *
     * @param orgId   机构ID
     * @param deptIds 部门ID字符串
     * @return 职员列表
     */
    List<Worker> getWorkersByDeptParentId(@Param("orgId") String orgId, @Param("deptIds") String deptIds);

    /**
     * 获取机构部门职员
     *
     * @param page   分页对象
     * @param deptId 部门ID
     * @return 结果对象
     */
    IPage<Worker> getOrgDeptWorkers(Page<Worker> page, @Param("deptId") String deptId);

}