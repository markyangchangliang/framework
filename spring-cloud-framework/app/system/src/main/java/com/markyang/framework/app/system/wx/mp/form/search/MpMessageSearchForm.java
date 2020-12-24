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
 * 微信消息(MpMessage)搜索表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-04-16 11:10:42
 */
@ApiModel(value = "微信消息搜索表单", description = "微信消息搜索表单类")
@NoArgsConstructor
@SuperBuilder
@Data
public class MpMessageSearchForm implements SearchBaseForm {

 /**
   * 昵称
   */
  @ApiModelProperty(value = "昵称,varchar(200)", position = 1)
  @SearchCondition(strategy = ConditionEnum.LIKE)
  private String nickname;
  /**
   * 已读标记
   */
  @ApiModelProperty(value = "已读标记,char(2)", position = 2)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String readFlag;
 /**
  * openId
  */
 @ApiModelProperty(value = "openId,varchar(32)", position = 3)
 @SearchCondition(strategy = ConditionEnum.EQUAL)
 private String openId;
  /**
   * 消息类型（text：文本；image：图片等）
   */
  @ApiModelProperty(value = "消息类型（text：文本；image：图片等）,char(20)", position = 4)
  @SearchCondition(strategy = ConditionEnum.EQUAL)
  private String replyType;

}
