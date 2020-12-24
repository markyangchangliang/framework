package com.markyang.framework.util;

import com.markyang.framework.util.exception.ResponseUtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * 响应处理工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public final class ResponseUtils {

    /**
     * 响应内容
     * @param response http响应对象
     * @param status 状态码
     * @param content 内容
     */
    public static void responseJson(HttpServletResponse response, int status, Object content) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        try {
            response.getWriter().write(JsonUtils.toJson(content));
        } catch (IOException e) {
            // 处理错误
            log.error("响应出现错误：", e);
            throw new ResponseUtilException("响应出现错误：" + e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param response 响应对象
     * @param filename 文件名称
     * @param inputStream 输入流
     */
    public static void download(HttpServletResponse response, String filename, InputStream inputStream) {
        try {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            // 处理错误
            log.error("响应出现错误：", e);
            throw new ResponseUtilException("响应出现错误：" + e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param response 响应对象
     * @param filename 文件名称
     * @param streamWriter 流写入函数
     */
    public static void download(HttpServletResponse response, String filename, Consumer<OutputStream> streamWriter) {
        try {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
            streamWriter.accept(response.getOutputStream());
        } catch (IOException e) {
            // 处理错误
            log.error("响应出现错误：", e);
            throw new ResponseUtilException("响应出现错误：" + e.getMessage());
        }
    }
}
