package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.HelpSearchForm;
import com.markyang.framework.app.system.service.HelpService;
import com.markyang.framework.app.system.repository.HelpRepository;
import com.markyang.framework.pojo.entity.system.Help;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 帮助文件(Help)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class HelpServiceImpl extends AbstractSearchableServiceImpl<Help, HelpRepository, HelpSearchForm> implements HelpService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public Help create() {
        return Help.builder().build();
    }
}