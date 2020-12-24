package com.markyang.framework.app.system.wx.mp.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.wx.mp.form.search.MpAutoReplySearchForm;
import com.markyang.framework.app.system.wx.mp.service.MpAutoReplyService;
import com.markyang.framework.app.system.wx.enumeration.WechatStatusEnum;
import com.markyang.framework.app.system.wx.exception.WechatException;
import com.markyang.framework.app.system.wx.mp.repository.MpAutoReplyRepository;
import com.markyang.framework.pojo.entity.system.wx.MpAutoReply;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信自动回复(MpAutoReply)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
@Service
@AllArgsConstructor
public class MpAutoReplyServiceImpl extends AbstractSearchableServiceImpl<MpAutoReply, MpAutoReplyRepository, MpAutoReplySearchForm> implements MpAutoReplyService {

    private final MpAutoReplyRepository repository;

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public MpAutoReply create() {
        return MpAutoReply.builder().build();
    }

    /**
     * 添加前校验，关键词不允许重复
     * @param entity 实体对象
     */
    @Override
    public void beforeAdd(MpAutoReply entity) {
        if (entity.getType().equals(WechatStatusEnum.Autoreply3.getValue())) {
            //关键词重复检验
            if (matchKeyWord(entity)) {
                throw new WechatException("关键词重复！");
            }
            if (StringUtils.isBlank(entity.getReplyMatchedType())) {
                throw new WechatException("匹配类型不能为空！");
            }
        }
    }


    /**
     * 做关键词匹配校验
     * @param entity 传入对象
     * @return boolean
     */
    private boolean matchKeyWord(MpAutoReply entity){
        if (StringUtils.equals(entity.getType(),WechatStatusEnum.Autoreply2.getValue())) {
            List<MpAutoReply> searchedList = this.list(Wrappers.<MpAutoReply>lambdaQuery()
                .eq(MpAutoReply::getRequestKeyword, entity.getRequestKeyword())
                .eq(MpAutoReply::getReplyMatchedType, entity.getReplyMatchedType()));
            return searchedList.size() > 0 && StringUtils.equals(entity.getRequestKeyword(), searchedList.get(0).getRequestKeyword());
        }
        return false;
    }
}