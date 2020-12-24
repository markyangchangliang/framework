package com.markyang.framework.pojo.web;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 响应数据
 * @author yangchangliang
 */
@ApiModel(value = "响应类", description = "全局规范统一Api返回格式的响应类")
@Data
@AllArgsConstructor
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 7873323263303551040L;
    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码", allowableValues = "0, 1", required = true, position = 1, notes = "0表示成功，1表示失败")
    private int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应信息", required = true, position = 2, notes = "对响应结果的一个简单描述", example = FrameworkConstants.GENERIC_SUCCESS_TIP)
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据", required = false, position = 3, notes = "接口响应的数据")
    private T data;

    /**
     * 判断是否成功
     *
     * @return bool
     */
    public boolean isSuccess() {
        return Objects.equals(this.code, 0);
    }

    public static <T> ResultVo<T> create(int code, String message, T data) {
        return new ResultVo<>(code, message, data);
    }

    public static <T> ResultVo<T> create(int code, String message) {
        return create(code, message, null);
    }

    public static <T> ResultVo<T> success(String message, T data) {
        return create(0, message, data);
    }

    public static <T> ResultVo<T> success(String message) {
        return success(message, null);
    }

    public static <T> ResultVo<T> error(int code, String message, T data) {
        return create(code, message, data);
    }

    public static <T> ResultVo<T> error(int code, String message) {
        return error(code, message, null);
    }

    public static <T> ResultVo<T> error(String message, T data) {
        return error(1, message, data);
    }

    public static <T> ResultVo<T> error(String message) {
        return error(message, null);
    }
}
