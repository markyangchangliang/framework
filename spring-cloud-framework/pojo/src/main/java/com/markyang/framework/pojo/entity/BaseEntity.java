package com.markyang.framework.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MybatisPlus基础实体类
 *
 * @author yangchangliang
 * @date 2020/5/12 4:32 下午 星期二
 * @version 1
 */
@NoArgsConstructor
@SuperBuilder
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createdDatetime;

    /**
     * 最后修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String lastModifiedBy;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime lastModifiedDatetime;
}
