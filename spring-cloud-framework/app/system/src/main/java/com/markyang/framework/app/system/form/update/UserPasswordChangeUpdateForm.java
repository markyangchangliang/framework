package com.markyang.framework.app.system.form.update;

import com.markyang.framework.service.core.form.UpdateBaseForm;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * 用户密码更改表单对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/26 4:38 下午 星期日
 */
@NoArgsConstructor
@SuperBuilder
@Data
public class UserPasswordChangeUpdateForm implements UpdateBaseForm {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码{required}")
    private String oldPassword;
    /**
     * 新密码
     */
    @NotBlank(message = "新密码{required}")
    private String newPassword;
}
