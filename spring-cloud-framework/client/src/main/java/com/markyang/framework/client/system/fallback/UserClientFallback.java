package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.UserClient;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.dto.system.OrgUserDto;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Client熔断类
 * @author yangchangliang
 */
@Component
public class UserClientFallback implements UserClient {

    /**
     * 根据用户名加载授权用户信息
     *
     * @param username 用户名
     * @return 授权用户
     */
    @Override
    public ResultVo<AuthenticatedUser> getUserByUsername(String username) {
        return ResultVo.error("用户服务连接异常");
    }

    /**
     * 根据用户名加载授权用户信息
     *
     * @param phone 手机号
     * @return 授权用户
     */
    @Override
    public ResultVo<AuthenticatedUser> getUserByMobilePhone(String phone) {
        return ResultVo.error("用户服务连接异常");
    }

    /**
     * 根据用户ID加载授权用户信息
     *
     * @param userId 用户ID
     * @return 授权用户
     */
    @Override
    public ResultVo<AuthenticatedUser> getUserByUserId(String userId) {
        return ResultVo.error("用户服务连接异常");
    }

    /**
     * 获取所有的用户信息
     *
     * @param orgId 机构ID
     * @return 结果对象
     */
    @Override
    public ResultVo<List<OrgUserDto>> getOrgUsers(String orgId) {
        return ResultVo.error("用户服务连接异常");
    }
}
