package com.markyang.framework.service.core.service.support;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 钩子上下文对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/29 6:21 下午 星期日
 */
@AllArgsConstructor(staticName = "of")
public class HookContext {

    private final Map<String, Object> data = Maps.newHashMapWithExpectedSize(128);

    public void setData(String key, Object value) {
        data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        return (T) data.get(key);
    }

    public boolean hasData(String key) {
        return data.containsKey(key);
    }

    public <T> T getDataIfAbsent(String key, Supplier<T> supplier) {
        if (this.hasData(key)) {
            return this.getData(key);
        }
        return supplier.get();
    }
}
