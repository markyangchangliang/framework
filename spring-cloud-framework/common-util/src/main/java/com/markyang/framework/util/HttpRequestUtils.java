package com.markyang.framework.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 封装http请求工具类（基于OkHttp实现）
 * @author yangchangliang
 */
@Slf4j
public final class HttpRequestUtils {

    /**
     * 请求client
     */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    /**
     * 发送POST请求
     * @param url 请求url
     * @param content 请求内容
     * @return 响应字符串
     */
    public static String post(String url, String content) {
        return HttpRequestUtils.post(url, MediaType.APPLICATION_JSON_VALUE, content);
    }

    /**
     * 发送POST请求
     * @param url 请求url
     * @param mediaType 请求内容类型
     * @param content 请求内容
     * @return 响应字符串
     */
    public static String post(String url, String mediaType, String content) {
        return HttpRequestUtils.post(url, null, mediaType, content);
    }

    /**
     * 发送POST请求
     * @param url 请求url
     * @param headers 请求头信息
     * @param content 请求内容
     * @return 响应字符串
     */
    public static String post(String url, Map<String, String> headers, String content) {
        return HttpRequestUtils.post(url, headers, org.springframework.http.MediaType.APPLICATION_JSON_VALUE, content);
    }

    /**
     * 发送POST请求
     * @param url 请求url
     * @param headers 请求头信息
     * @param mediaType 内容类型
     * @param content 请求实体内容（json字符串）
     * @return 响应字符串
     */
    public static String post (String url, Map<String, String> headers, String mediaType, String content) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (headers != null) {
            headers.forEach(builder::header);
        }
        Request request = builder.post(RequestBody.create(content, okhttp3.MediaType.get(mediaType))).build();

        ResponseBody responseBody;
        try {
            responseBody = HttpRequestUtils.OK_HTTP_CLIENT.newCall(request).execute().body();
        } catch (IOException e) {
            log.error("POST请求[{}]出现错误：{}", url, e);
            return "";
        }

        if (responseBody == null) {
            log.warn("POST请求[{}]返回NULL，可能是请求被缓存", url);
            return "";
        }

        try {
            return responseBody.string();
        } catch (IOException e) {
            log.error("POST请求[{}]成功，但是将请求体转为字符串时出现错误：{}", url, e);
            return "";
        }
    }

    /**
     * 发送GET请求
     * @param url 请求url
     * @return 响应字符串
     */
    public static String get(String url) {
        return HttpRequestUtils.get(url, null);
    }

    /**
     * 发送GET请求
     * @param url 请求url
     * @param headers 请求头信息
     * @return 响应字符串
     */
    public static String get(String url, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (Objects.nonNull(headers)) {
            headers.forEach(builder::header);
        }
        Request request = builder.get().build();
        ResponseBody responseBody;
        try {
            responseBody = HttpRequestUtils.OK_HTTP_CLIENT.newCall(request).execute().body();
        } catch (IOException e) {
            log.error("GET请求[{}]出现错误：{}", url, e);
            return "";
        }

        if (responseBody == null) {
            log.warn("GET请求[{}]返回NULL，可能是请求被缓存", url);
            return "";
        }

        try {
            return responseBody.string();
        } catch (IOException e) {
            log.error("GET请求[{}]成功，但是将请求体转为字符串时出现错误：{}", url, e);
            return "";
        }
    }

}
