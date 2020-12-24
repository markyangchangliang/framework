package com.markyang.framework.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.markyang.framework.pojo.entity.AbstractBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 文件上传(File)实体类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 17:26:45
 */
@ApiModel(value = "文件上传实体类", description = "文件上传实体类")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`sys_file`")
public class File extends AbstractBaseEntity {

    private static final long serialVersionUID = 550426414581187862L;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型,int(11)", position = 1)
    private Integer type;
    /**
     * URL地址
     */
    @ApiModelProperty(value = "URL地址,varchar(256)", position = 2)
    private String url;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间,datetime", position = 3)
    private LocalDateTime createDate;

}