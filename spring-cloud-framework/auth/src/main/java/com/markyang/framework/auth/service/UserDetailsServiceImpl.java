package com.markyang.framework.auth.service;

import com.markyang.framework.auth.exception.PhoneNotFoundException;
import com.markyang.framework.auth.exception.UserNotFoundException;
import com.markyang.framework.client.system.UserClient;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 用户信息实现类
 * @author markyang
 */
@Service("authUserDetailsServiceImpl")
@Primary
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements FrameworkUserDetailsService, SocialUserDetailsService {

    private final UserClient userClient;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("加载用户名密码登录的用户[{}]信息", username);
        ResultVo<AuthenticatedUser> resultVo = this.userClient.getUserByUsername(username);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        }
        log.error("加载用户名密码登录的用户[{}]信息失败：{}", username, resultVo.getMessage());
        throw new UsernameNotFoundException(resultVo.getMessage());
    }

    /**
     * 根据手机号加载用户
     *
     * @param phone 手机号
     * @return 用户详情
     * @throws PhoneNotFoundException 手机号找不到异常
     */
    @Override
    public UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException {
        log.info("加载手机号登录的用户[{}]信息", phone);
        ResultVo<AuthenticatedUser> resultVo = this.userClient.getUserByMobilePhone(phone);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        }
        log.error("加载手机号登录的用户[{}]信息失败：{}", phone, resultVo.getMessage());
        throw new PhoneNotFoundException(resultVo.getMessage());
    }

    /**
     * 社区账号登录
     * @param userId 用户ID
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("加载社区账号登录的用户[{}]信息", userId);
        ResultVo<AuthenticatedUser> resultVo = this.userClient.getUserByUserId(userId);
        if (resultVo.isSuccess()) {
            return resultVo.getData();
        }
        log.error("加载社区账号登录的用户[{}]信息失败：{}", userId, resultVo.getMessage());
        throw new UserNotFoundException(resultVo.getMessage());
    }
}
