package com.markyang.framework.config.message;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 消息配置
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@ConfigurationProperties(prefix = "framework.message")
@Data
public class MessageConfigProperties {

    /**
     * 映射存储类型
     */
    private MappingStoreType mappingStoreType;

    /**
     * 业务标识符与消息通道的映射关系配置
     */
    private Map<String, MessageChannelConfig> businessKeyChannelNamesMapping = Maps.newHashMap();

    /**
     * 消息通道执行类型
     */
    @Getter
    @AllArgsConstructor
    public enum ChannelExecutionType {
        /**
         * 直接执行
         */
        DIRECT("a"),
        /**
         * 按规则执行
         */
        RULE("b"),
        /**
         * 啥也不干
         */
        NOTHING("c");

        private final String value;
    }

    /**
     * 映射存储类型
     */
    public enum MappingStoreType {
        /**
         * 数据库存储类型
         */
        JDBC,
        /**
         * yml配置存储类型
         */
        YML
    }

    /**
     * 消息通道配置
     */
    @Data
    public static class MessageChannelConfig {

        /**
         * 消息通道执行类型
         */
        private ChannelExecutionType executionType = ChannelExecutionType.DIRECT;

        /**
         * 规则名称
         */
        private String rulesName;

        /**
         * 消息通道名称列表
         */
        private List<String> messageChannels;
    }
}
