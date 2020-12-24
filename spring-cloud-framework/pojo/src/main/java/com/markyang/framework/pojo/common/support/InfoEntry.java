package com.markyang.framework.pojo.common.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用信息Entry
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/16 3:57 下午 星期四
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoEntry {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * logo
     */
    private String logo;

    public static InfoEntry of(String id, String name) {
        return of(id, name, 1, null);
    }

    public static InfoEntry of(String id, String name, Integer seq) {
        return new InfoEntry(id, name, seq, null);
    }

    public static InfoEntry of(String id, String name, Integer seq, String logo) {
        return new InfoEntry(id, name, seq, logo);
    }

}
