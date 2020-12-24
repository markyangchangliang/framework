package com.markyang.framework.service.core.service.hook.shape;

import com.markyang.framework.service.common.FieldDictTranslatorService;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookAdapter;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.support.DtoDictField;
import com.markyang.framework.util.ConversionUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * DTO对象数据字典解析钩子
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/28 1:31 下午 星期六
 */
@ConditionalOnProperty(prefix = "framework", name = "field-dict-translator-enabled", havingValue = "true")
@Component
@AllArgsConstructor
@Order(1)
public class DtoDictResolverHook extends ServiceHookAdapter {

    /**
     * APP ID
     */
    private static final String APP_ID = FrameworkConstants.REGIONAL_MEDICAL_APP_ID;
    private final FieldDictTranslatorService fieldDictTranslatorService;

    /**
     * 查询之后
     *
     * @param dto  实体类
     * @param context 钩子上下文对象
     */
    @Override
    public void postGet(Object dto, HookContext context) {
        Class<?> entityClass = dto.getClass();
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(entityClass, DtoDictField.class);

        // 解析字段名
        fields.parallelStream().forEach(field -> {
            String dtoFieldKey = entityClass.getName() + "." + field.getName();
            // 尝试从上下文对象中直接拿
            Map<String, String> dictMap = context.getDataIfAbsent(dtoFieldKey, () -> {
                DtoDictField dtoDictField = field.getAnnotation(DtoDictField.class);
                String tableName = dtoDictField.tableName();
                String fieldName = this.resolveFieldName(field, dtoDictField.fieldName());
                List<DictDto> dictList = this.fieldDictTranslatorService.getFieldDict(APP_ID, tableName, fieldName, false);
                Map<String, String> map = dictList.parallelStream().collect(Collectors.toMap(DictDto::getValue, DictDto::getName));
                context.setData(dtoFieldKey, map);
                return map;
            });

            if (MapUtils.isEmpty(dictMap)) {
                // 无数据字典，直接返回
                return;
            }

            ReflectionOperationUtils.getFieldValue(field, dto).ifPresent(value -> {
                String dictMean = dictMap.get(value.toString());
                if (Objects.nonNull(dictMean)) {
                    // 设置解析值
                    ReflectionOperationUtils.setFieldValue(entityClass, field.getName() + FrameworkConstants.FIELD_DICT_MEAN_FIELD_SUFFIX, dto, dictMean);
                }
            });
        });
    }

    /**
     * 解析字段名称
     * @param field 字段对象
     * @param fieldName 字段名称
     * @return 名称
     */
    private String resolveFieldName(Field field, String fieldName) {
        if (StringUtils.isNotBlank(fieldName)) {
            return fieldName;
        }
        return ConversionUtils.camelToDash(field.getName());
    }
}
