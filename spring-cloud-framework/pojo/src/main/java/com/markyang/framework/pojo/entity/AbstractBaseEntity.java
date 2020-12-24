package com.markyang.framework.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 抽象实体基类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/14 11:05 上午 星期四
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public abstract class AbstractBaseEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    protected String id;
}
