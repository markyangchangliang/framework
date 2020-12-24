package com.markyang.framework.app.system.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.dto.system.DeptUserDto;
import com.markyang.framework.pojo.dto.system.OrgUserDto;
import com.markyang.framework.pojo.dto.system.UserProfileDto;
import com.markyang.framework.pojo.entity.system.User;
import com.markyang.framework.service.core.repository.FrameworkRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户仓库
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 4:34 下午 星期一
 */
public interface UserRepository extends FrameworkRepository<User> {

    /**
     * 获取部门所有用户
     * @param page 分页对象
     * @param deptId 部门ID
     * @return 部门用户
     */
    IPage<DeptUserDto> getDeptUsers(Page<DeptUserDto> page, @Param("deptId") String deptId);

    /**
     * 获取机构所有的用户
     * @param orgId 机构ID
     * @return 用户列表
     */
    List<OrgUserDto> getOrgUsers(@Param("orgId") String orgId);

    /**
     * 获取用户基本信息
     * @param userId 用户ID
     * @return 基本信息
     */
    UserProfileDto getUserProfile(@Param("userId") String userId);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 授权用户
     */
    AuthenticatedUser getUserByUsername(@Param("username") String username);

    /**
     * 根据手机号获取用户
     * @param mobilePhone 手机号
     * @return 授权用户
     */
    AuthenticatedUser getUserByMobilePhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 根据用户ID获取用户
     * @param userId 用户ID
     * @return 授权用户
     */
    AuthenticatedUser getUserByUserId(@Param("userId") String userId);
}
