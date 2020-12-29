package com.markyang.framework.auth.service;

import com.markyang.framework.auth.exception.PhoneNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户信息
 * @author markyang
 */
public interface FrameworkUserDetailsService extends UserDetailsService {

    /**
     * 根据手机号加载用户
     * @param phone 手机号
     * @return 用户详情
     * @throws PhoneNotFoundException 手机号找不到异常
     */
    UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException;
}
