package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.UserClientFallback;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.dto.system.OrgUserDto;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * client接口调用
 * @author yangchangliang
 */
@Service
@FeignClient(name = "system", fallback = UserClientFallback.class)
public interface UserClient {

    /**
     * 根据用户名加载授权用户信息
     * @param username 用户名
     * @return 授权用户
     */
    @GetMapping("/user/username/{username}")
    ResultVo<AuthenticatedUser> getUserByUsername(@PathVariable String username);

    /**
     * 根据用户名加载授权用户信息
     * @param phone 手机号
     * @return 授权用户
     */
    @GetMapping("/user/phone/{phone}")
    ResultVo<AuthenticatedUser> getUserByMobilePhone(@PathVariable String phone);

    /**
     * 根据用户ID加载授权用户信息
     * @param userId 用户ID
     * @return 授权用户
     */
    @GetMapping("/user/userId/{userId}")
    ResultVo<AuthenticatedUser> getUserByUserId(@PathVariable String userId);

    /**
     * 获取所有的用户信息
     * @param orgId 机构ID
     * @return 结果对象
     */
    @GetMapping("/user/orgUsers/{orgId}")
    ResultVo<List<OrgUserDto>> getOrgUsers(@PathVariable String orgId);
}
