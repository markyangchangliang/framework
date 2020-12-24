package com.markyang.framework.pojo.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表信息数结构对象
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/30 12:14 下午 星期六
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableInfoTreeDto {

    /**
     * 数据库名称
     */
    private String label;
    /**
     * 表信息
     */
    private List<TableInfoDto> children;
}
