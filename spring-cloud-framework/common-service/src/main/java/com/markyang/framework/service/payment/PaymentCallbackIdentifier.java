package com.markyang.framework.service.payment;

import com.markyang.framework.pojo.enumeration.payment.ServiceProviderEnum;

/**
 * 回调标识接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface PaymentCallbackIdentifier {

    /**
     * 是否支持处理
     * @param serviceProvider 服务提供商
     * @param businessKey 业务标识符
     * @return bool
     */
    boolean support(ServiceProviderEnum serviceProvider, String businessKey);
}
