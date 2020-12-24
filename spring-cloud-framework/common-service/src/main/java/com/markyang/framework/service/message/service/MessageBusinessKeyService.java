package com.markyang.framework.service.message.service;

import com.markyang.framework.service.core.service.SearchableService;
import com.markyang.framework.service.message.form.search.MessageBusinessKeySearchForm;
import com.markyang.framework.pojo.entity.message.MessageBusinessKey;

import java.util.Optional;

/**
 * 消息业务Key服务接口
 *
 * @author yangchangliang
 * @version 1
 */
public interface MessageBusinessKeyService extends SearchableService<MessageBusinessKey, MessageBusinessKeySearchForm> {

    /**
     * 根据业务Key名称获取业务Key
     * @param orgId 机构ID
     * @param keyName key名称
     * @return 消息业务Key optional对象
     */
    Optional<MessageBusinessKey> getBusinessKey(String orgId, String keyName);
}
