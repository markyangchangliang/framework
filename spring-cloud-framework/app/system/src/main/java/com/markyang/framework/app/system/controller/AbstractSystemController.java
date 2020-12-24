package com.markyang.framework.app.system.controller;

import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.service.core.controller.AbstractFrameworkController;
import com.markyang.framework.service.core.form.SearchBaseForm;
import com.markyang.framework.service.core.form.UpdateBaseForm;
import com.markyang.framework.service.core.service.SearchableService;

/**
 * 系统模块控制器基类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/28 10:57 上午 星期六
 */
public abstract class AbstractSystemController<E extends BaseEntity, SS extends SearchableService<E, S>, S extends SearchBaseForm, U extends UpdateBaseForm> extends AbstractFrameworkController<E, SS, S, U> {

    /**
     * 默认的应用ID
     * @return 应用ID
     */
    @Override
    public String getAppId() {
        return FrameworkConstants.REGIONAL_MEDICAL_APP_ID;
    }
}
