package com.markyang.framework.pojo.common.type.concrete;

import com.markyang.framework.pojo.common.type.Range;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 时间范围类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/16 2:01 下午 星期六
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocalDateRange implements Range<LocalDate> {
    /**
     * 开始时间
     */
    private LocalDate begin;
    /**
     * 结束时间
     */
    private LocalDate end;
    /**
     * 获取开始值
     *
     * @return 开始值
     */
    @Override
    public LocalDate getBegin() {
        return this.begin;
    }

    /**
     * 获取结束值
     *
     * @return 结束值
     */
    @Override
    public LocalDate getEnd() {
        return this.end;
    }
}
