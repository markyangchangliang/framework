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
 * 微信自动回复(MpAutoReply)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信自动回复搜索表单", description = "微信自动回复搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class MpAutoReplySearchForm implements SearchBaseForm {

  /**
   * 关键字
   */
  @ApiModelProperty(value = "关键字,varchar(64)", position = 3)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String requestKeyword;
  /**
   * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
   */
  @ApiModelProperty(value = "请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）,char(10)", position = 4)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String requestType;
  /**
   * 请求消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置）
   */
  @ApiModelProperty(value = "类型（1、关注时回复；2、消息回复；3、关键词回复）", position = 5)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String type;
  /**
   * 回复类型文本匹配类型（1、全匹配，2、半匹配）
   */
  @ApiModelProperty(value = "回复类型文本匹配类型（1、全匹配，2、半匹配）,char(10)", position = 6)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String replyMatchedType;

}
