package com.markyang.framework.app.system.wx.mp.form.search;

import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.search.ConditionEnum;
import com.markyang.framework.service.core.search.SearchCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 微信用户(MpUser)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信用户搜索表单", description = "微信用户搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class MpUserSearchForm implements SearchBaseForm {

  /**
   * 昵称
   */
  @ApiModelProperty(value = "昵称 ", allowableValues = "", position = 1, notes = "")
  @SearchCondition(strategy = ConditionEnum.LIKE)
  private String nickname;
  /**
   * 电话
   */
  @ApiModelProperty(value = "电话", allowableValues = "", position = 2, notes = "")
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String phone;
  /**
   * 用户备注
   */
  @ApiModelProperty(value = "用户备注,varchar(100)", position = 1)
  @SearchCondition(strategy = ConditionEnum.LIKE)
  private String remark;
  /**
   * 应用类型
   */
  @ApiModelProperty(value = "应用类型,char(2)", position = 2)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String appType;
  /**
   * 是否订阅
   */
  @ApiModelProperty(value = "是否订阅,char(2)", position = 3)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String subscribe;
  /**
   * 用户关注渠道来源
   */
  @ApiModelProperty(value = "用户关注渠道来源 ,varchar(50)", position = 4)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String subscribeScene;
  /**
   * 关注次数
   */
  @ApiModelProperty(value = "关注次数 ,int", position = 6)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private Integer subscribeNum;
  /**
   * 用户标识
   */
  @ApiModelProperty(value = "用户标识 ,varchar(64)", position = 8)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String openId;
  /**
   * 性别
   */
  @ApiModelProperty(value = "性别 ,char(2)", position = 10)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String gender;
  /**
   * 所在城市
   */
  @ApiModelProperty(value = "所在城市 ,varchar(64)", position = 11)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String city;
  /**
   * 所在国家
   */
  @ApiModelProperty(value = "所在国家 ,varchar(64)", position = 12)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String country;
  /**
   * 所在省份
   */
  @ApiModelProperty(value = "所在省份 ,varchar(64)", position = 13)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String province;

  /**
   * 用户语言
   */
  @ApiModelProperty(value = "用户语言,varchar(64)", position = 15)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String language;
  /**
   * 用户组
   */
  @ApiModelProperty(value = "用户组,varchar(64)", position = 18)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String groupId;
  /**
   * 标签列表
   */
  @ApiModelProperty(value = "标签列表,varchar(64)", position = 19)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String tagIdList;
  /**
   * 二维码扫码场景
   */
  @ApiModelProperty(value = "二维码扫码场景,varchar(64)", position = 20)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String qrScene;

}
