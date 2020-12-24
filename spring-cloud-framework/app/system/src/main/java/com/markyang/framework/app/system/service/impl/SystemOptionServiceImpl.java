package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.SystemOptionSearchForm;
import com.markyang.framework.app.system.service.SystemOptionService;
import com.markyang.framework.app.system.repository.SystemOptionRepository;
import com.markyang.framework.pojo.entity.system.SystemOption;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统选项(SystemOption)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class SystemOptionServiceImpl extends AbstractSearchableServiceImpl<SystemOption, SystemOptionRepository, SystemOptionSearchForm> implements SystemOptionService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public SystemOption create() {
        return SystemOption.builder().build();
    }
}