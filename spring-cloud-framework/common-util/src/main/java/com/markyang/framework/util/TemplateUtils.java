package com.markyang.framework.util;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 模板处理工具类
 * @author yangchangliang
 */
@Slf4j
@Component
public final class TemplateUtils {

    /**
     * Freemarker模板引擎配置类
     */
    private static Configuration configuration;

    @Autowired
    public TemplateUtils(Configuration configuration) {
        configuration.setOutputEncoding(StandardCharsets.UTF_8.name());
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configuration.setDateTimeFormat(FrameworkConstants.DATE_TIME_PATTERN);
        configuration.setDateFormat(FrameworkConstants.DATE_PATTERN);
        configuration.setTimeFormat(FrameworkConstants.TIME_PATTERN);
        configuration.setBooleanFormat(Boolean.TRUE.toString() + "," + Boolean.FALSE.toString());
        TemplateUtils.configuration = configuration;
    }

    /**
     * 解析一个模板
     * @param templatePath 模板路径
     * @param data 模板数据
     * @param writer 输出Writer
     */
    public static void render(String templatePath, Object data, Writer writer) {
        try {
            Template template = configuration.getTemplate(templatePath, StandardCharsets.UTF_8.name());
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            log.error("模板渲染失败：{}", e.getMessage());
        }
    }

    /**
     * 解析一个模板为字符串
     * @param templatePath 模板路径
     * @param data 模板数据
     * @return 解析后的字符串Optional对象
     */
    public static Optional<String> renderToString(String templatePath, Object data) {
        StringBuilderWriter writer = new StringBuilderWriter();
        TemplateUtils.render(templatePath, data, writer);
        String content = writer.toString();
        writer.close();
        if (StringUtils.isBlank(content)) {
            return Optional.empty();
        }
        return Optional.of(content);
    }

}
