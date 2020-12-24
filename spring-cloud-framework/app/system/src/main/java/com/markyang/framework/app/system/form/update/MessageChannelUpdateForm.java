package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.AbstractUpdateBaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * 消息通道更新表单
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/11 4:47 下午 星期一
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class MessageChannelUpdateForm extends AbstractUpdateBaseForm {

    /**
     * 通道名称
     */
    @NotBlank(message = "通道名称{required}")
    private String name;
    /**
     * 通道配置
     */
    private String configuration;
    /**
     * 备注
     */
    private String remark;
}
