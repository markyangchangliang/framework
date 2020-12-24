package com.markyang.framework.pojo.constant;

import feign.Retryer;
import feign.httpclient.ApacheHttpClient;
import feign.okhttp.OkHttpClient;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Feign调用常量
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/14 4:41 下午 星期二
 */
public interface FeignClientConstants {

    /**
     * OkHttp客户端对象
     */
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    /**
     * HttpClient客户端对象
     */
    ApacheHttpClient APACHE_HTTP_CLIENT = new ApacheHttpClient();
    /**
     * 重试器
     */
    Retryer RETRYER = new Retryer.Default(100, SECONDS.toMillis(1), 3);
}
