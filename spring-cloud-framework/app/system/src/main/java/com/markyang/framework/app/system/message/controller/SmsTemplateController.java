package com.markyang.framework.app.system.message.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.message.form.search.SmsTemplateSearchForm;
import com.markyang.framework.app.system.message.form.update.SmsTemplateUpdateForm;
import com.markyang.framework.app.system.message.service.SmsTemplateService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.message.SmsTemplate;
import com.markyang.framework.pojo.web.ResultVo;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 短信模板(SmsTemplate)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-05-28 17:41:05
 */
@Api(tags = "短信模板控制器")
@ApiSort(1)
@RestController
@CacheName("smsTemplate")
@RequestMapping("/smsTemplate")
@Slf4j
@AllArgsConstructor
public class SmsTemplateController extends AbstractFrameworkController<SmsTemplate, SmsTemplateService, SmsTemplateSearchForm, SmsTemplateUpdateForm> {

    private final SmsTemplateService smsTemplateService;

    /**
     * 是否是机构所属数据
     *
     * @return bool
     */
    @Override
    public boolean isOrgBelonged() {
        return true;
    }

    /**
     * 一键更新短信模板
     *
     * @return 结果对象
     */
    @GetMapping("/synchronization")
    public ResultVo<List<SmsTemplate>> synchronization() {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.smsTemplateService.synchronization());
    }

}