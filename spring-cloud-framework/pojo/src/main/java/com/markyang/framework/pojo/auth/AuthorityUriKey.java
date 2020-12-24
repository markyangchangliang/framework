package com.markyang.framework.pojo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * URI权限Key
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/1 11:18 上午 星期三
 */
@Data
@AllArgsConstructor(staticName = "of")
public class AuthorityUriKey {

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求URI
     */
    private String uri;
}
