package com.markyang.framework.pojo.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页数据对象
 *
 * @author yangchangliang
 * @version 1
 */
@ApiModel(value = "分页数据对象", description = "需要分页的接口返回的数据对象")
@Data
@AllArgsConstructor(staticName = "of")
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = -7783686175592694111L;
    /**
     * 当前页码
     */
    @ApiModelProperty(name = "page", value = "当前页码", position = 1, example = "1")
    private Integer page;
    /**
     * 分页大小
     */
    @ApiModelProperty(name = "size", value = "分页大小", position = 2, example = "20")
    private Integer size;
    /**
     * 数据总数
     */
    @ApiModelProperty(name = "total", value = "数据总数", position = 3, example = "100")
    private Long total;
    /**
     * 本页数据
     */
    @ApiModelProperty(name = "content", value = "本页数据", position = 4)
    private List<T> content;

    /**
     * 转换MybatisPlus自带的Page
     *
     * @param page page对象
     * @param <T>  泛型
     * @return PageVo对象
     */
    public static <T> PageVo<T> of(IPage<T> page) {
        return PageVo.of((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), page.getRecords());
    }
}
