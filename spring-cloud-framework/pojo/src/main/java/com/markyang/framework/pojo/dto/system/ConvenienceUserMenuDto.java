package com.markyang.framework.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* 用户便捷菜单(SysConvenienceUserMenu)实体类
*
* @author yangchangliang
* @version 1
* @date 2020-05-08 17:32:09
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvenienceUserMenuDto implements Serializable {

   private static final long serialVersionUID = 84039273290938414L;
   /**
    * 主键
    */
   private String id;
   /**
   * 菜单ID
   */
   private String menuId;
   /**
    * 菜单标题
    */
   private String title;
   /**
    * 菜单URI
    */
   private String uri;
   /**
   * 排序
   */
   private Integer seq;

}