package com.markyang.framework.service.common;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.dto.system.DeptInfoDto;
import com.markyang.framework.pojo.dto.system.OrgConfigDto;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import com.markyang.framework.pojo.dto.system.UserWorkerInfoDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.service.common.repository.CommonInfoCacheRepository;
import com.markyang.framework.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 公用信息缓存服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class CommonInfoCacheService {

    private final CommonInfoCacheRepository commonInfoCacheRepository;

    public CommonInfoCacheService(@Autowired(required = false) CommonInfoCacheRepository commonInfoCacheRepository) {
        this.commonInfoCacheRepository = commonInfoCacheRepository;
    }

    /**
     * 获取所有机构信息
     * @return 机构信息
     */
    public List<OrgInfoDto> getOrgInfo() {
        List<OrgInfoDto> orgInfos = this.commonInfoCacheRepository.getOrgInfo();
        return orgInfos.parallelStream()
            .peek(this::handleOrgInfoDto)
            .collect(Collectors.toList());
    }

    /**
     * 处理OrgInfoDto
     * @param orgInfoDto dto对象
     */
    private void handleOrgInfoDto(OrgInfoDto orgInfoDto) {
        String[] arrHisWorkerInfo = StringUtils.split(orgInfoDto.getHisWorker(), "~~");
        if (ArrayUtils.isNotEmpty(arrHisWorkerInfo) && arrHisWorkerInfo.length == 3) {
            orgInfoDto.setHisDeptId(arrHisWorkerInfo[0]);
            orgInfoDto.setHisWorker(arrHisWorkerInfo[1]);
            orgInfoDto.setHisWorkerPwd(arrHisWorkerInfo[2]);
        }
        if (StringUtils.isBlank(orgInfoDto.getConfig())) {
            return;
        }
        orgInfoDto.setConfigDto(JsonUtils.fromJson(orgInfoDto.getConfig(), OrgConfigDto.class));
        orgInfoDto.setConfig(null);
    }

    /**
     * 获取所有部门信息
     * @return 部门信息
     */
    public List<DeptInfoDto> getDeptInfo() {
        return this.commonInfoCacheRepository.getDeptInfo();
    }

    /**
     * 获取所有的用户职员信息
     * @return 用户职员信息
     */
    public List<UserWorkerInfoDto> getUserWorkerInfo() {
        return this.commonInfoCacheRepository.getUserWorkerInfo();
    }

    /**
     * 获取所有的职员信息
     * @return 职员信息
     */
    public List<UserWorkerInfoDto> getWorkerInfo() {
        return this.commonInfoCacheRepository.getWorkerInfo();
    }

    /**
     * 缓存机构信息
     */
    public void cacheOrgInfo() {
        this.getOrgInfo().parallelStream().forEach(orgInfoDto -> RedisUtils.set(OrgInfoUtils.ORG_INFO_CACHE_KEY + orgInfoDto.getId(), orgInfoDto));
    }

    /**
     * 更新缓存的机构信息
     * @param org 机构对象
     */
    public void updateCachedOrgInfo(Org org) {
        OrgInfoDto orgInfoDto = new OrgInfoDto();
        BeanUtils.copyProperties(org, orgInfoDto);
        this.handleOrgInfoDto(orgInfoDto);
        RedisUtils.set(OrgInfoUtils.ORG_INFO_CACHE_KEY + org.getId(), orgInfoDto);
    }

    /**
     * 删除缓存的机构信息
     * @param orgId 机构ID
     */
    public void deleteCachedOrgInfo(String orgId) {
        RedisUtils.remove(OrgInfoUtils.ORG_INFO_CACHE_KEY + orgId);
    }

    /**
     * 删除所有的机构信息
     */
    public void deleteAllCachedOrgInfo() {
        RedisUtils.removeAll(OrgInfoUtils.ORG_INFO_CACHE_KEY);
    }

    /**
     * 缓存部门信息
     */
    public void cacheDeptInfo() {
        this.getDeptInfo().parallelStream().forEach(deptInfoDto -> RedisUtils.set(DeptInfoUtils.DEPT_INFO_CACHE_KEY + deptInfoDto.getId(), deptInfoDto));
    }

    /**
     * 更新缓存的部门信息
     * @param dept 部门对象
     */
    public void updateCachedDeptInfo(Dept dept) {
        DeptInfoDto deptInfoDto = new DeptInfoDto();
        BeanUtils.copyProperties(dept, deptInfoDto);
        RedisUtils.set(DeptInfoUtils.DEPT_INFO_CACHE_KEY + dept.getId(), deptInfoDto);
    }

    /**
     * 删除缓存的部门信息
     * @param deptId 部门ID
     */
    public void deleteCachedDeptInfo(String deptId) {
        RedisUtils.remove(DeptInfoUtils.DEPT_INFO_CACHE_KEY + deptId);
    }

    /**
     * 删除所有的部门信息
     */
    public void deleteAllCachedDeptInfo() {
        RedisUtils.removeAll(DeptInfoUtils.DEPT_INFO_CACHE_KEY);
    }

    /**
     * 缓存用户职员信息
     */
    public void cacheUserWorkerInfo() {
        // 缓存用户职员信息
        this.getUserWorkerInfo().parallelStream().forEach(userWorkerInfoDto -> RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "userId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getId(), userWorkerInfoDto));
        // 缓存职员信息
        this.getWorkerInfo().parallelStream().forEach(userWorkerInfoDto -> {
            RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "workerId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getWorkerId(), userWorkerInfoDto);
            RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "hisUserId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getOrgId() + "-" + userWorkerInfoDto.getOrgUserId(), userWorkerInfoDto);
        });
    }

    /**
     * 更新缓存的用户职员信息
     * @param userId 用户ID
     */
    public void updateCachedUserWorkerInfo(String userId) {
        UserWorkerInfoDto userWorkerInfoDto = this.commonInfoCacheRepository.getUserWorker(userId);
        if (Objects.isNull(userWorkerInfoDto)) {
            return;
        }
        RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "userId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getId(), userWorkerInfoDto);
    }

    /**
     * 更新缓存的职员信息
     * @param workerId 职员ID
     */
    public void updateCachedWorkerInfo(String workerId) {
        UserWorkerInfoDto userWorkerInfoDto = this.commonInfoCacheRepository.getWorker(workerId);
        if (Objects.isNull(userWorkerInfoDto)) {
            return;
        }
        RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "workerId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getWorkerId(), userWorkerInfoDto);
        RedisUtils.set(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "hisUserId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getOrgId() + "-" + userWorkerInfoDto.getOrgUserId(), userWorkerInfoDto);
    }

    /**
     * 删除缓存的用户职员信息
     * @param userId 用户ID
     * @param workerId 职员ID
     */
    public void deleteCachedUserWorkerInfo(String userId, String workerId) {
        if (Objects.nonNull(userId)) {
            RedisUtils.remove(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "userId" + CacheConstants.CACHE_KEY_SEPARATOR + userId);
        }
        if (Objects.nonNull(workerId)) {
            UserWorkerInfoUtils.getUserWorkerInfoByWorkerId(workerId).ifPresent(userWorkerInfoDto -> {
                RedisUtils.remove(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "hisUserId" + CacheConstants.CACHE_KEY_SEPARATOR + userWorkerInfoDto.getOrgId() + "-" + userWorkerInfoDto.getOrgUserId());
            });
            RedisUtils.remove(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY + "workerId" + CacheConstants.CACHE_KEY_SEPARATOR + workerId);
        }
    }

    /**
     * 删除所有的用户职员信息
     */
    public void deleteAllCachedUserWorkerInfo() {
        RedisUtils.removeAll(UserWorkerInfoUtils.USER_WORKER_INFO_CACHE_KEY);
    }

}
