package com.markyang.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

/**
 * 对象工具类
 *
 * @author yangchangliang
 * @version 1
 */
@Slf4j
public final class ObjectUtils {

    /**
     * 将对象美化为字符串
     * @param object 对象
     * @return 字符串
     */
    public static String stringify(Object object) {
        return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * 序列化一个对象为字符串
     * @param object 对象
     * @return 序列化后的字符串（Base64）
     */
    public static Optional<String> serializeObjectToString(Object object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            log.warn("对象[{}]序列化失败：{}", stringify(object), e.getMessage());
            return Optional.empty();
        }
        return Optional.of(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
    }

    /**
     * 反序列化一个字符串为对象
     * @param objectString 字符串
     * @return 反序列化后的对象，如果可以序列化成功的话
     */
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> deserializeObjectFromString(String objectString) {
        byte[] bytes = Base64.getDecoder().decode(objectString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return Optional.of((T) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            log.warn("字符串对象[{}]反序列化失败：{}", objectString, e.getMessage());
            return Optional.empty();
        }
    }
}
