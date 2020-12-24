package com.markyang.framework.pojo.dto.system;

import lombok.Data;

/**
 * 数据字典
 *
 * @author yangchangliang
 * @date 2017-12-20 14:57:46
 */
@Data
public class FieldDictDto {
    //序号
    private int index;
    // 数据字典显示时的编号
    private String id;
    // 数据字典显示时的名称
    private String name;
    //选中标志
    private String selectedSign;
    //复选标志
    private boolean checked;
    // remark
    private String remark;
}
