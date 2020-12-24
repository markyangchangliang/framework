package com.markyang.framework.pojo.common.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * KV键值对类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/24 10:21 上午 星期二
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ItemEntry implements Serializable {

    private static final long serialVersionUID = 1246763532621716536L;

    public static ItemEntry of(String key, String value) {
        return of(key, value, null);
    }

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 排序
     */
    private Integer seq;
}
