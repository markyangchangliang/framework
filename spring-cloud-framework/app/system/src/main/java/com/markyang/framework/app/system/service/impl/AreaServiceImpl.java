package com.markyang.framework.app.system.service.impl;


import com.markyang.framework.app.system.form.search.AreaSearchForm;
import com.markyang.framework.app.system.service.AreaService;
import com.markyang.framework.app.system.repository.AreaRepository;
import com.markyang.framework.pojo.entity.system.Area;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 区域信息(Area)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class AreaServiceImpl extends AbstractSearchableServiceImpl<Area, AreaRepository, AreaSearchForm> implements AreaService {


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Area create() {
        return Area.builder().build();
    }
}