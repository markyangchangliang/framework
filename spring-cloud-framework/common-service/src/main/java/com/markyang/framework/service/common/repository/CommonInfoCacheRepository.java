package com.markyang.framework.service.common.repository;

import com.markyang.framework.pojo.dto.system.DeptInfoDto;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import com.markyang.framework.pojo.dto.system.UserWorkerInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公用缓存仓库
 *
 * @author yangchangliang
 * @version 1
 */
public interface CommonInfoCacheRepository {

    /**
     * 获取机构信息
     * @return 机构信息
     */
    List<OrgInfoDto> getOrgInfo();

    /**
     * 获取部门信息
     * @return 部门信息
     */
    List<DeptInfoDto> getDeptInfo();

    /**
     * 获取用户职员信息
     * @return 用户职员信息
     */
    List<UserWorkerInfoDto> getUserWorkerInfo();

    /**
     * 获取职员信息
     * @return 职员信息
     */
    List<UserWorkerInfoDto> getWorkerInfo();

    /**
     * 获取用户职员信息
     * @param userId 用户ID
     * @return 用户职员信息
     */
    UserWorkerInfoDto getUserWorker(@Param("userId") String userId);

    /**
     * 获取职员信息
     * @param workerId 职员ID
     * @return 职员信息
     */
    UserWorkerInfoDto getWorker(@Param("workerId") String workerId);
}
