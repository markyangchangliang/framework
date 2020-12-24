package com.markyang.framework.service.core.service.hook.shape;

import com.markyang.framework.service.common.FieldDictTranslatorService;
import com.markyang.framework.service.core.service.support.HookContext;
import com.markyang.framework.service.core.service.support.ServiceHookAdapter;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.BaseEntity;
import com.markyang.framework.pojo.entity.support.DictField;
import com.markyang.framework.util.EntityUtils;
import com.markyang.framework.util.ReflectionOperationUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典解析钩子
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/28 1:31 下午 星期六
 */
@ConditionalOnProperty(prefix = "framework", name = "field-dict-translator-enabled", havingValue = "true")
@Component
@AllArgsConstructor
@Order(0)
public class FieldDictResolverHook extends ServiceHookAdapter {

    /**
     * APP ID
     */
    private static final String APP_ID = FrameworkConstants.REGIONAL_MEDICAL_APP_ID;
    private final FieldDictTranslatorService fieldDictTranslatorService;


    /**
     * 查询之后
     *
     * @param entity 实体类
     * @param context 钩子上下文对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public void postGet(Object entity, HookContext context) {
        if (!(entity instanceof BaseEntity)) {
            return;
        }
        Class<? extends BaseEntity> entityClass = (Class<? extends BaseEntity>) entity.getClass();
        // 拿到表名
        String tableName = EntityUtils.resolveRawTableName(entityClass);
        List<Field> fields = ReflectionOperationUtils.getAnnotatedFields(entityClass, DictField.class);
        Map<String, Map<String, List<DictDto>>> map = context.getDataIfAbsent(entityClass.getName(), () -> {
            Map<String, Map<String, List<DictDto>>> dictMap = getDictMap(tableName);
            context.setData(entityClass.getName(), dictMap);
            return dictMap;
        });
        // 解析字段名
        fields.parallelStream().forEach(field -> {
            DictField dictField = field.getAnnotation(DictField.class);
            String fieldName = StringUtils.isNotBlank(dictField.fieldName()) ? dictField.fieldName() : EntityUtils.resolveRawFieldName(field);
            if (StringUtils.isNotBlank(dictField.tableName())) {
                String fieldKey = entityClass.getName() + "." + field.getName();
                Map<String, String> dictMap = context.getDataIfAbsent(fieldKey, () -> {
                    List<DictDto> dictList = this.fieldDictTranslatorService.getFieldDict(APP_ID, dictField.tableName(), fieldName, false);
                    Map<String, String> newMap = dictList.parallelStream().collect(Collectors.toMap(DictDto::getValue, DictDto::getName));
                    context.setData(fieldKey, newMap);
                    return newMap;
                });
                if (MapUtils.isEmpty(dictMap)) {
                    return;
                }
                ReflectionOperationUtils.getFieldValue(field, entity).ifPresent(value -> {
                    // 设置解析值
                    String meaning = dictMap.get(value.toString());
                    if (StringUtils.isNotBlank(meaning)) {
                        ReflectionOperationUtils.setFieldValue(entityClass, field.getName() + FrameworkConstants.FIELD_DICT_MEAN_FIELD_SUFFIX, entity, meaning);
                    }
                });
                return;
            }

            Map<String, List<DictDto>> fieldDict = map.get(fieldName);
            if (MapUtils.isEmpty(fieldDict)) {
                return;
            }
            ReflectionOperationUtils.getFieldValue(field, entity).ifPresent(value -> {
                List<DictDto> dictMean = fieldDict.get(value.toString());
                if (CollectionUtils.isNotEmpty(dictMean)) {
                    // 设置解析值
                    ReflectionOperationUtils.setFieldValue(entityClass, field.getName() + FrameworkConstants.FIELD_DICT_MEAN_FIELD_SUFFIX, entity, dictMean.get(0).getName());
                }
            });
        });
    }

    /**
     * 拿到表中的数据字典集合
     * @param tableName 表名
     * @return 字典集合
     */
    private Map<String, Map<String, List<DictDto>>> getDictMap(String tableName) {
        // 拿到表所有的数据字典信息
        Map<String, List<DictDto>> allFieldDict = this.fieldDictTranslatorService.getAllFieldDict(APP_ID, tableName, false);
        return allFieldDict.entrySet()
            .parallelStream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey, entry -> entry.getValue()
                        .parallelStream()
                        .collect(Collectors.groupingByConcurrent(DictDto::getValue))
                )
            );
    }

}
