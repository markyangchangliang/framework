package com.markyang.framework.pojo.dto.tencent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * 身份证信息Dto
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/27 5:08 下午 星期一
 */
@Data
@Builder
@ApiModel("身份证信息")
public class IdentityCardInfoDto {

    /**
     * 姓名（人像面）
     */
    @ApiModelProperty("姓名（人像面）")
    private String name;
    /**
     * 性别（人像面）
     */
    @ApiModelProperty("性别（人像面）")
    private String sex;
    /**
     * 民族（人像面）
     */
    @ApiModelProperty("民族（人像面）")
    private String nation;
    /**
     * 出生日期（人像面）
     */
    @ApiModelProperty("出生日期（人像面）")
    private LocalDate birth;
    /**
     * 地址（人像面）
     */
    @ApiModelProperty("地址（人像面）")
    private String address;
    /**
     * 身份证号（人像面）
     */
    @ApiModelProperty("身份证号（人像面）")
    private String identityCardNumber;
    /**
     * 发证机关（国徽面）
     */
    @ApiModelProperty("发证机关（国徽面）")
    private String authority;
    /**
     * 证件有效期（国徽面）
     */
    @ApiModelProperty("证件有效期（国徽面）")
    private String validDate;
}
