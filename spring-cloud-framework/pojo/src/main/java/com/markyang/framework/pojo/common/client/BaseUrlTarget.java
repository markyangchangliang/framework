package com.markyang.framework.pojo.common.client;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 基地址Target
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/17 4:35 下午 星期日
 */
@AllArgsConstructor
@EqualsAndHashCode
public class BaseUrlTarget<T> implements Target<T> {

    private final String baseUrl;
    private final String name;
    private final Class<T> target;

    @Override
    public Class<T> type() {
        return this.target;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String url() {
        return this.baseUrl;
    }

    /**
     * Targets a template to this target, adding the {@link #url() base url} and any target-specific
     * headers or query parameters. <br>
     * <br>
     * For example: <br>
     *
     * <pre>
     * public Request apply(RequestTemplate input) {
     *   input.insert(0, url());
     *   input.replaceHeader(&quot;X-Auth&quot;, currentToken);
     *   return input.asRequest();
     * }
     * </pre>
     *
     * <br>
     * <br>
     * <br>
     * <b>relationship to JAXRS 2.0</b><br>
     * <br>
     * This call is similar to {@code
     * javax.ws.rs.client.WebTarget.request()}, except that we expect transient, but necessary
     * decoration to be applied on invocation.
     *
     * @param input
     */
    @Override
    public Request apply(RequestTemplate input) {
        if (input.url().indexOf("http") != 0) {
            input.target(this.url());
        }
        return input.request();
    }

    public static <T> BaseUrlTarget<T> create(String baseUrl, Class<T> target) {
        return new BaseUrlTarget<>(baseUrl, "baseUrl:" + target.getSimpleName(), target);
    }
}
