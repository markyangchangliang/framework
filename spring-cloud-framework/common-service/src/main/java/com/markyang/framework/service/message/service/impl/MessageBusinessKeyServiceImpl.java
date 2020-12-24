package com.markyang.framework.service.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.pojo.entity.message.MessageBusinessKey;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.service.message.form.search.MessageBusinessKeySearchForm;
import com.markyang.framework.service.message.repository.MessageBusinessKeyRepository;
import com.markyang.framework.service.message.service.MessageBusinessKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 消息业务key实现类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@Slf4j
public class MessageBusinessKeyServiceImpl extends AbstractSearchableServiceImpl<MessageBusinessKey, MessageBusinessKeyRepository, MessageBusinessKeySearchForm> implements MessageBusinessKeyService {

    /**
     * 根据业务Key名称获取业务Key
     *
     * @param orgId 机构ID
     * @param keyName key名称
     * @return 消息业务Key optional对象
     */
    @Cacheable(cacheNames = "messageBusinessKey", unless = "#result == null")
    @Override
    public Optional<MessageBusinessKey> getBusinessKey(String orgId, String keyName) {
        return Optional.ofNullable(this.getOne(Wrappers.<MessageBusinessKey>lambdaQuery().eq(MessageBusinessKey::getOrgId, orgId).eq(MessageBusinessKey::getKeyName, keyName)));
    }

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public MessageBusinessKey create() {
        return MessageBusinessKey.builder().build();
    }
}
