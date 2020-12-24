package com.markyang.framework.app.system.config;

import com.markyang.framework.app.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
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
@Service("systemUserDetailsServiceImpl")
@AllArgsConstructor
@Primary
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

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
        return this.userService.getByUsername(username).orElseThrow(()
            -> new UsernameNotFoundException("用户名不存在"));
    }
}
