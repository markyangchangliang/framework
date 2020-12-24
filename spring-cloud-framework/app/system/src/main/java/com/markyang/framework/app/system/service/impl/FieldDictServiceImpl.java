package com.markyang.framework.app.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.markyang.framework.app.system.form.search.FieldDictSearchForm;
import com.markyang.framework.app.system.service.FieldDictService;
import com.markyang.framework.app.system.repository.FieldDictRepository;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.FieldDict;
import com.markyang.framework.service.core.service.impl.AbstractSearchableServiceImpl;
import com.markyang.framework.util.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 数据字典(FieldDict)服务类实现
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-25 18:18:11
 */
@Service
@AllArgsConstructor
public class FieldDictServiceImpl extends AbstractSearchableServiceImpl<FieldDict, FieldDictRepository, FieldDictSearchForm> implements FieldDictService {

    /**
     * 数据字典缓存KEY
     */
    private static final String FIELD_DICT_CACHE_PREFIX = CacheConstants.CACHE_PREFIX
        + CacheConstants.CACHE_KEY_SEPARATOR + "fieldDict" + CacheConstants.CACHE_KEY_SEPARATOR;
    /**
     * 数据字典缓存天数 30天
     */
    private static final Duration CACHE_DAYS = Duration.ofDays(30);

    private final FieldDictRepository repository;


    /**
     * 创建空的或者带有默认值的实体对象
     *
     * @return 实体对象
     */
    @Override
    public FieldDict create() {
        return FieldDict.builder().build();
    }

    @Override
    public void beforeAdd(FieldDict entity) {
        if (Objects.isNull(entity.getSeq())) {
            entity.setSeq(99);
        }
    }

    @Override
    public void beforeUpdate(FieldDict entity) {
        if (Objects.isNull(entity.getSeq())) {
            entity.setSeq(99);
        }
    }

    /**
     * 获取表列表
     *
     * @param appId 应用ID
     * @return 表名列表
     */
    @Override
    public List<ItemEntry> getAllTables(String appId) {
        return this.repository.getAllTables(appId);
    }

    /**
     * 获取表字段列表
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @return 表名列表
     */
    @Override
    public List<ItemEntry> getAllTableFields(String appId, String tableName) {
        return this.repository.getAllTableFields(appId, tableName);
    }

    /**
     * 获取表中所有的数据字典字段
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @return 字典
     */
    @Override
    public Map<String, List<DictDto>> getAllFieldDict(String appId, String tableName) {
        String key = FIELD_DICT_CACHE_PREFIX + "getAllFieldDict" + CacheConstants.CACHE_KEY_SEPARATOR
            + appId + CacheConstants.CACHE_KEY_SEPARATOR + tableName;
        ConcurrentMap<String, List<DictDto>> fieldDictMap = RedisUtils.get(key);
        if (Objects.nonNull(fieldDictMap)) {
            return fieldDictMap;
        }
        List<FieldDict> fieldDictList = this.list(Wrappers.<FieldDict>lambdaQuery()
            .select(FieldDict::getFieldName, FieldDict::getFieldValue, FieldDict::getFieldMean,
                FieldDict::getSeq).eq(FieldDict::getAppId, appId).eq(FieldDict::getTableName, tableName));
        fieldDictMap = fieldDictList.parallelStream()
                .map(fieldDict ->
                    DictDto.of(fieldDict.getFieldName(), fieldDict.getFieldMean(), fieldDict.getFieldValue(), fieldDict.getSeq()))
                .collect(Collectors.groupingByConcurrent(DictDto::getField));
        // 缓存30天
        RedisUtils.set(key, fieldDictMap, CACHE_DAYS);
        return fieldDictMap;
    }

    /**
     * 获取表中某一字段的字典
     *
     * @param appId     应用ID
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 字典
     */
    @Override
    public List<DictDto> getFieldDict(String appId, String tableName, String fieldName) {
        String key = FIELD_DICT_CACHE_PREFIX + "getFieldDict" + CacheConstants.CACHE_KEY_SEPARATOR + appId
            + CacheConstants.CACHE_KEY_SEPARATOR + tableName + CacheConstants.CACHE_KEY_SEPARATOR + fieldName;
        List<DictDto> dictDtoList = RedisUtils.get(key);
        if (Objects.nonNull(dictDtoList)) {
            return dictDtoList;
        }
        List<FieldDict> fieldDictList = this.list(Wrappers.<FieldDict>lambdaQuery()
            .select(FieldDict::getFieldName, FieldDict::getFieldValue, FieldDict::getFieldMean,
                FieldDict::getSeq).eq(FieldDict::getAppId, appId)
            .eq(FieldDict::getTableName, tableName)
            .eq(FieldDict::getFieldName,fieldName));
        dictDtoList = fieldDictList.parallelStream().map(fieldDict
            -> DictDto.of(fieldDict.getFieldName(), fieldDict.getFieldMean(), fieldDict.getFieldValue(),
            fieldDict.getSeq())).collect(Collectors.toList());
        // 缓存30天
        RedisUtils.set(key, dictDtoList, CACHE_DAYS);
        return dictDtoList;
    }

    /**
     * 获取表列表
     *
     * @param appId      应用ID
     * @param tableName  表名
     * @param fieldName  字段名称
     * @param fieldValue 字典值
     * @param fieldMean  字典值含义
     * @return 表名列表
     */
    @Override
    public List<FieldDict> getGeneratedInsertSql(String appId, String tableName, String fieldName, String fieldValue, String fieldMean) {
        FieldDictSearchForm searchForm = FieldDictSearchForm.builder()
                .appId(appId)
                .tableName(tableName)
                .fieldName(fieldName)
                .fieldValue(fieldValue)
                .fieldMean(fieldMean)
                .build();
        return this.getSearchedList(searchForm);
    }
}