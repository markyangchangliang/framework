package com.markyang.framework.service.core.service.support;

import com.markyang.framework.service.core.service.hook.ServiceHook;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务类钩子持有类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 4:26 下午 星期二
 */
@Component
public final class ServiceHookHolder {

    public static List<ServiceHook> serviceHooks;

    public ServiceHookHolder(List<ServiceHook> serviceHooks) {
        ServiceHookHolder.serviceHooks = serviceHooks;
    }
}
