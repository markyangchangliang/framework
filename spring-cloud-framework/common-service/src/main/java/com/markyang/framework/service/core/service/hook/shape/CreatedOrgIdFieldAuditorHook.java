package com.markyang.framework.service.core.service.hook.shape;

import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookAdapter;
import com.markyang.framework.pojo.auditor.CreatedOrgId;
import com.markyang.framework.util.AuthUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 创建时机构ID自动审计钩子
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/4/24 6:50 下午 星期五
 */
@Component
public class CreatedOrgIdFieldAuditorHook extends ServiceHookAdapter {

    /**
     * 添加之前
     *
     * @param object  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void preAdd(Object object, HookContext context) {
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(object.getClass(), CreatedOrgId.class);
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        String orgId = AuthUtils.getLoggedUserOrgId();
        for (Field field : fields) {
            ReflectionOperationUtils.setFieldValue(field, object, orgId);
        }
    }
}
