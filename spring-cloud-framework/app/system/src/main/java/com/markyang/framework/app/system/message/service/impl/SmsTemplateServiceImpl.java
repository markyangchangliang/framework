package com.markyang.framework.app.system.message.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.*;
import com.markyang.framework.app.base.exception.UpdateDeniedException;
import com.markyang.framework.app.system.exception.SystemException;
import com.markyang.framework.app.system.message.enumeration.MessageEnum;
import com.markyang.framework.app.system.message.form.search.SmsTemplateSearchForm;
import com.markyang.framework.app.system.message.repository.SmsTemplateRepository;
import com.markyang.framework.app.system.message.service.SmsTemplateService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.message.SmsTemplate;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 短信模板(SmsTemplate)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:40:39
 */
@Service
@AllArgsConstructor
public class SmsTemplateServiceImpl extends AbstractSearchableServiceImpl<SmsTemplate, SmsTemplateRepository, SmsTemplateSearchForm> implements SmsTemplateService {

    private final SmsClient smsClient;

    @Override
    public void beforeAdd(SmsTemplate entity) {
        if (Objects.isNull(entity)) {
            throw new UpdateDeniedException("参数不能为空！");
        }
        AddSmsTemplateRequest request = new AddSmsTemplateRequest();
        request.setSmsType(0L);
        request.setTemplateName(entity.getTemplateName());
        request.setTemplateContent(entity.getTemplateContent());
        request.setInternational(0L);
        request.setRemark(entity.getTemplateRemark());
        try {
            //腾讯云模板添加成功，本地无须判断模板id是否重复
            AddSmsTemplateResponse addSmsTemplateResponse = this.smsClient.AddSmsTemplate(request);
            entity.setTemplateId(addSmsTemplateResponse.getAddTemplateStatus().getTemplateId());
        } catch (TencentCloudSDKException exception) {
            exception.printStackTrace();
            throw new UpdateDeniedException("添加腾讯云短信模板失败！");
        }
        entity.setTemplateAuditResult(MessageEnum.AUDITED.getValue());
    }

    @Override
    public void beforeUpdate(SmsTemplate entity) {
        List<SmsTemplate> list =
            this.list(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, AuthUtils.getLoggedUserOrgId())
                .eq(SmsTemplate::getTemplateId, entity.getTemplateId()).eq(SmsTemplate::getChannel, entity.getChannel()));
        if (CollectionUtils.isEmpty(list)) {
            throw new UpdateDeniedException("需要修改的数据不存在！");
        }
        ModifySmsTemplateRequest modifySmsTemplateRequest = new ModifySmsTemplateRequest();
        modifySmsTemplateRequest.setTemplateContent(entity.getTemplateContent());
        modifySmsTemplateRequest.setTemplateName(entity.getTemplateName());
        modifySmsTemplateRequest.setTemplateId(Long.valueOf(entity.getTemplateId()));
        modifySmsTemplateRequest.setSmsType(0L);
        modifySmsTemplateRequest.setInternational(0L);
        modifySmsTemplateRequest.setRemark(entity.getTemplateRemark());
        try {
            this.smsClient.ModifySmsTemplate(modifySmsTemplateRequest);
        } catch (TencentCloudSDKException exception) {
            exception.printStackTrace();
            throw new UpdateDeniedException("修改腾讯云短信模板失败！");
        }
    }

    @Override
    public void beforeDelete(SmsTemplate entity) {
        List<SmsTemplate> list =
            this.list(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, AuthUtils.getLoggedUserOrgId())
                .eq(SmsTemplate::getTemplateId, entity.getTemplateId()).eq(SmsTemplate::getChannel, entity.getChannel()));
        if (CollectionUtils.isEmpty(list)) {
            throw new UpdateDeniedException("需要删除的数据不存在！");
        }
        DeleteSmsTemplateRequest deleteSmsTemplateRequest = new DeleteSmsTemplateRequest();
        deleteSmsTemplateRequest.setTemplateId(Long.valueOf(entity.getTemplateId()));
        try {
            this.smsClient.DeleteSmsTemplate(deleteSmsTemplateRequest);
        } catch (TencentCloudSDKException exception) {
            exception.printStackTrace();
            throw new UpdateDeniedException("删除腾讯云短信模板失败！");
        }
    }

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public SmsTemplate create() {
        return SmsTemplate.builder().build();
    }

    /**
     * 一键更新短信模板
     *
     * @return 短信模板列表
     */
    @Override
    public List<SmsTemplate> synchronization() {
        List<SmsTemplate> templates = this.list(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, AuthUtils.getLoggedUserOrgId()));
        ArrayList<String> arrayList = new ArrayList<>();
        for (SmsTemplate smsTemplate : templates) {
            arrayList.add(smsTemplate.getTemplateId());
        }
        String[] templateIds = arrayList.toArray(new String[0]);
        DescribeSmsTemplateListRequest describeSmsTemplateListRequest = new DescribeSmsTemplateListRequest();
        describeSmsTemplateListRequest.setInternational(0L);
        if (ArrayUtils.isEmpty(templateIds)) {
            throw new SystemException("传入短信模板id为空！");
        }
        Long[] longIds = Arrays.stream(templateIds).parallel().map(Long::valueOf).toArray(Long[]::new);
        describeSmsTemplateListRequest.setTemplateIdSet(longIds);
        List<SmsTemplate> smsTemplates = null;
        try {
            DescribeSmsTemplateListResponse describeSmsTemplateListResponse =
                this.smsClient.DescribeSmsTemplateList(describeSmsTemplateListRequest);
            DescribeTemplateListStatus[] describeTemplateStatusSet =
                describeSmsTemplateListResponse.getDescribeTemplateStatusSet();
            for (SmsTemplate smsTemplate : templates) {
                for (DescribeTemplateListStatus describeTemplateListStatus : describeTemplateStatusSet) {
                    //templateId相同的更新本地记录
                    if (StringUtils.equals(String.valueOf(describeTemplateListStatus.getTemplateId()), smsTemplate.getTemplateId())) {
                        if (Objects.equals(describeTemplateListStatus.getStatusCode(), 0L)) {
                            smsTemplate.setTemplateStatus(MessageEnum.THROUGH.getValue());
                            smsTemplate.setTemplateAuditResult(MessageEnum.PAST.getValue());
                        }
                        if (Objects.equals(describeTemplateListStatus.getStatusCode(), -1L)) {
                            smsTemplate.setTemplateStatus(MessageEnum.REFUSED.getValue());
                            smsTemplate.setTemplateAuditResult(MessageEnum.UNPAST.getValue() + FrameworkConstants.COMMA_SEPARATOR +
                                " " + describeTemplateListStatus.getReviewReply());
                        }
                        this.update(smsTemplate, Wrappers.<SmsTemplate>lambdaUpdate()
                            .eq(SmsTemplate::getOrgId, AuthUtils.getLoggedUserOrgId())
                            .eq(SmsTemplate::getTemplateId, describeTemplateListStatus.getTemplateId())
                            .eq(SmsTemplate::getChannel, smsTemplate.getChannel()));
                    }
                }
            }
            smsTemplates = this.list(Wrappers.<SmsTemplate>lambdaQuery().eq(SmsTemplate::getOrgId, AuthUtils.getLoggedUserOrgId()));
        } catch (TencentCloudSDKException exception) {
            exception.printStackTrace();
        }
        return smsTemplates;
    }
}