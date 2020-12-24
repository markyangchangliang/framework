package com.markyang.framework.app.system.service.impl;

import com.markyang.framework.app.system.form.search.ItemFeeRatioSearchForm;
import com.markyang.framework.app.system.service.ItemFeeRatioService;
import com.markyang.framework.app.system.repository.ItemFeeRatioRepository;
import com.markyang.framework.pojo.entity.system.ItemFeeRatio;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 项目分成比例(ItemFeeRatio)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
public class ItemFeeRatioServiceImpl extends AbstractSearchableServiceImpl<ItemFeeRatio, ItemFeeRatioRepository, ItemFeeRatioSearchForm> implements ItemFeeRatioService {

    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public ItemFeeRatio create() {
        return ItemFeeRatio.builder().build();
    }
}