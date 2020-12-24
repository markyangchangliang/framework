package com.markyang.framework.service.core.repository;

import com.markyang.framework.config.mybatis.FrameworkMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 框架基本仓库
 * @author yangchangliang
 */
public interface FrameworkRepository<E> extends FrameworkMapper<E> {
}