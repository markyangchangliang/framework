package com.markyang.framework.app.system.message.service;

import com.markyang.framework.app.system.message.form.search.SmsTemplateSearchForm;
import com.markyang.framework.pojo.entity.system.message.SmsTemplate;
import com.markyang.framework.service.core.service.SearchableService;

import java.util.List;

/**
 * 短信模板(SmsTemplate)服务类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:40:39
 */
public interface SmsTemplateService extends SearchableService<SmsTemplate, SmsTemplateSearchForm> {
    /**
     * 一键更新短信模板
     * @return 短信模板列表
     */
    List<SmsTemplate> synchronization();
}