package com.markyang.framework.config.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 框架Mapper
 *
 * @author yangchangliang
 * @version 1
 */
public interface FrameworkMapper<T> extends BaseMapper<T> {

    /**
     * 总是更新一些列的字段
     * @param entity 实体对象
     * @return 影响行数
     */
    int alwaysUpdateSomeColumnById(T entity);

    /**
     * 批量插入指定行数的数据
     * @param entities 实体对象列表
     * @return 影响行数
     */
    int insertBatchSomeColumn(List<T> entities);

    /**
     * 根据ID逻辑删除时填充信息
     * @param entity 实体对象
     * @return 影响行数
     */
    int deleteByIdWithFill(T entity);

    /**
     * 根据ID判断是否存在
     * @param id 主键ID
     * @return bool 是否存在
     */
    Boolean existsById(Serializable id);

    /**
     * 根据构建条件判断是否存在记录
     * @param queryWrapper 查询条件构造器
     * @return bool 是否存在
     */
    Boolean exists(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
