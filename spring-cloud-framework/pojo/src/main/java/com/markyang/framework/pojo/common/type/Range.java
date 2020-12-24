package com.markyang.framework.pojo.common.type;

/**
 * 范围接口
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/23 3:20 下午 星期一
 */
public interface Range<T> {

    /**
     * 获取开始值
     *
     * @return 开始值
     */
    T getBegin();

    /**
     * 获取结束值
     *
     * @return 结束值
     */
    T getEnd();
}
