package com.markyang.framework.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.StrictFill;
import com.google.common.collect.Lists;
import com.markyang.framework.pojo.auth.AuthenticatedPatient;
import com.markyang.framework.pojo.auth.AuthenticatedUser;
import com.markyang.framework.util.AuthUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author yangchangliang
 */
@Component
public class FrameworkMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        List<StrictFill> strictFills = Lists.newArrayList();
        if (metaObject.hasGetter("createdDatetime")) {
            strictFills.add(StrictFill.of("createdDatetime", LocalDateTime.class, now));
        }
        AuthenticatedUser user = AuthUtils.getLoggedUser(false);
        AuthenticatedPatient patient = null;
        if (Objects.isNull(user)) {
            patient = AuthUtils.getLoggedPatient(false);
        }
        if (metaObject.hasGetter("createdBy")) {
            if (Objects.nonNull(user)) {
                strictFills.add(StrictFill.of("createdBy", String.class, user.getUserId()));
            } else if (Objects.nonNull(patient)) {
                strictFills.add(StrictFill.of("createdBy", String.class, patient.getUserId()));
            }
        }
        if (metaObject.hasGetter("lastModifiedDatetime")) {
            strictFills.add(StrictFill.of("lastModifiedDatetime", LocalDateTime.class, now));
        }
        if (metaObject.hasGetter("lastModifiedBy")) {
            if (Objects.nonNull(user)) {
                strictFills.add(StrictFill.of("lastModifiedBy", String.class, user.getUserId()));
            } else if (Objects.nonNull(patient)) {
                strictFills.add(StrictFill.of("lastModifiedBy", String.class, patient.getUserId()));
            }
        }
        this.strictInsertFill(this.findTableInfo(metaObject), metaObject, strictFills);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        List<StrictFill> strictFills = Lists.newArrayList();
        AuthenticatedUser user = AuthUtils.getLoggedUser(false);
        AuthenticatedPatient patient = null;
        if (Objects.isNull(user)) {
            patient = AuthUtils.getLoggedPatient(false);
        }
        if (metaObject.hasGetter("lastModifiedDatetime")) {
            strictFills.add(StrictFill.of("lastModifiedDatetime", LocalDateTime.class, LocalDateTime.now()));
        }
        if (metaObject.hasGetter("lastModifiedBy")) {
            if (Objects.nonNull(user)) {
                strictFills.add(StrictFill.of("lastModifiedBy", String.class, user.getUserId()));
            } else if (Objects.nonNull(patient)) {
                strictFills.add(StrictFill.of("lastModifiedBy", String.class, patient.getUserId()));
            }
        }
        this.strictUpdateFill(this.findTableInfo(metaObject), metaObject, strictFills);
    }
}
