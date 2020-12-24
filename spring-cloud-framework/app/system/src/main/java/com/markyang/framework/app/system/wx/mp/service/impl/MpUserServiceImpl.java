package com.markyang.framework.app.system.wx.mp.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.markyang.framework.app.system.wx.mp.form.search.MpUserSearchForm;
import com.markyang.framework.app.system.wx.mp.service.MpUserService;
import com.markyang.framework.app.system.wx.enumeration.WechatStatusEnum;
import com.markyang.framework.app.system.wx.exception.WechatException;
import com.markyang.framework.app.system.wx.mp.repository.MpUserRepository;
import com.markyang.framework.pojo.entity.system.wx.MpUser;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.DateTimeUtils;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 微信用户(MpUser)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:09:22
 */
@Service
@AllArgsConstructor
public class MpUserServiceImpl extends AbstractSearchableServiceImpl<MpUser, MpUserRepository, MpUserSearchForm> implements MpUserService {

  private final WxMpService wxService;

  @Override
  public void beforeAdd(MpUser entity) {
    if (StringUtils.isBlank(entity.getOpenId())
        || StringUtils.isBlank(entity.getNickname())) {
      throw new WechatException("缺少用户信息！");
    }
  }

  /**
   * 创建空的或者带有默认值的实体对象
   * @return 实体对象
   */
  @Override
  public MpUser create() {
    return MpUser.builder().build();
  }

  /**
   * 同步微信用户信息
   *
   * @return 结果对象
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean synchron() throws WxErrorException {
    WxMpUserList userList;
    WxMpUserService wxMpUserService = wxService.getUserService();
    userList = wxMpUserService.userList(null);
    if (CollectionUtils.isEmpty(userList.getOpenids())) {
      throw new WechatException("该公众号暂无人关注！");
    }
    //先将用户信息移除
    this.remove(Wrappers.emptyWrapper());
    List<WxMpUser> listWxMpUser = getWxMpUserList(wxMpUserService, userList.getOpenids());
    listWxMpUser.forEach(wxMpUser -> {
      MpUser mpUser1 = setWxUserValue(wxMpUser);
      this.add(mpUser1);
    });
    //递归获取数据
    if (userList.getCount() >= 10000) {
      this.synchron();
    }
    return true;
  }
  //构造用户实体
  MpUser setWxUserValue(WxMpUser userWxInfo) {
    return MpUser.builder().appType(WechatStatusEnum.TypeApp.getValue())
        .avatarUrl(userWxInfo.getHeadImgUrl()).subscribe(String.valueOf(userWxInfo.getSubscribe()))
        .subscribeScene(userWxInfo.getSubscribeScene())
        .subscribeTime(DateTimeUtils.fromSecondTimestamp(userWxInfo.getSubscribeTime())).openId(userWxInfo.getOpenId()).nickname(userWxInfo.getNickname())
        .gender(String.valueOf(userWxInfo.getSex())).city(userWxInfo.getCity()).country(userWxInfo.getCountry())
        .province(userWxInfo.getProvince()).language(userWxInfo.getLanguage()).remark(userWxInfo.getRemark())
        .unionId(userWxInfo.getUnionId()).groupId(JSON.toJSONString(userWxInfo.getGroupId()))
        .tagIdList(Arrays.toString(userWxInfo.getTagIds())).qrScene(userWxInfo.getQrScene()).subscribeNum(1)
        .build();
  }

  /**
   * 分批次获取微信粉丝信息 每批100条
   */
  private List<WxMpUser> getWxMpUserList(WxMpUserService wxMpUserService, List<String> openidsList) throws WxErrorException {
    // 粉丝openid数量
    int count = openidsList.size();
    if (count <= 0) {
      return new ArrayList<>();
    }
    List<WxMpUser> list = Lists.newArrayList();
    List<WxMpUser> followersInfoList;
    int a = count % 100 > 0 ? count / 100 + 1 : count / 100;
    for (int i = 0; i < a; i++) {
      if (i + 1 < a) {
        followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, ((i + 1) * 100)));
        if (null != followersInfoList && !followersInfoList.isEmpty()) {
          list.addAll(followersInfoList);
        }
      } else {
        followersInfoList = wxMpUserService.userInfoList(openidsList.subList(i * 100, count));
        if (null != followersInfoList && !followersInfoList.isEmpty()) {
          list.addAll(followersInfoList);
        }
      }
    }
    return list;
  }

}
