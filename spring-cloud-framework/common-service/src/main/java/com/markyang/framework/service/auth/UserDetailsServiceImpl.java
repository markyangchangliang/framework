package com.markyang.framework.service.auth;

import com.markyang.framework.client.system.UserClient;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息加载服务类
 *
 * @author yangchangliang
 * @version 1
 */
@ConditionalOnBean(UserClient.class)
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

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
        log.info("加载用户[{}]信息", username);
        ResultVo<AuthenticatedUser> resultVo = this.userClient.getUserByUsername(username);
        if (resultVo.isSuccess()) {
            // 清除密码信息
            resultVo.getData().eraseCredentials();
            return resultVo.getData();
        }
        log.error("加载用户[{}]信息失败：{}", username, resultVo.getMessage());
        throw new UsernameNotFoundException("加载用户[" + username + "]信息失败：" + resultVo.getMessage());
    }
}
