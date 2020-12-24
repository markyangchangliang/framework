package com.markyang.framework.service.common;

import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.service.common.repository.FieldDictTranslatorRepository;
import com.markyang.framework.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据字典翻译服务类
 *
 * @author yangchangliang
 * @version 1
 */
@Service
public class FieldDictTranslatorService {

    /**
     * 数据字典缓存KEY
     */
    private static final String FIELD_DICT_CACHE_PREFIX = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "fieldDict" + CacheConstants.CACHE_KEY_SEPARATOR;
    /**
     * 数据字典缓存天数 30天
     */
    private static final Duration CACHE_DAYS = Duration.ofDays(30);

    private final FieldDictTranslatorRepository fieldDictTranslatorRepository;

    public FieldDictTranslatorService(@Autowired(required = false) FieldDictTranslatorRepository fieldDictTranslatorRepository) {
        this.fieldDictTranslatorRepository = fieldDictTranslatorRepository;
    }

    /**
     * 获取一个表所有的数据字典信息
     * @param appId 应用ID
     * @param tableName 表名
     * @return 字典信息
     */
    public Map<String, List<DictDto>> getAllFieldDict(String appId, String tableName, boolean filterDisplay) {
        String key = FIELD_DICT_CACHE_PREFIX + "getAllFieldDict" + CacheConstants.CACHE_KEY_SEPARATOR + appId + CacheConstants.CACHE_KEY_SEPARATOR + tableName;
        return RedisUtils.getIfAbsent(key, () -> {
            List<DictDto> allFieldDict = this.fieldDictTranslatorRepository.getAllFieldDict(appId, tableName, filterDisplay);
            Map<String, List<DictDto>> allFieldDictMap = allFieldDict.parallelStream()
                .collect(Collectors.groupingByConcurrent(DictDto::getField));
            // 缓存30天
            RedisUtils.set(key, allFieldDictMap, CACHE_DAYS);
            return allFieldDictMap;
        });
    }

    /**
     * 获取表中某一个字段的数据字典信息
     * @param appId 应用ID
     * @param tableName 表名
     * @param fieldName 字段名
     * @return 字典信息
     */
    public List<DictDto> getFieldDict(String appId, String tableName, String fieldName, boolean filterDisplay) {
        String key = FIELD_DICT_CACHE_PREFIX + "getFieldDict" + CacheConstants.CACHE_KEY_SEPARATOR + appId + CacheConstants.CACHE_KEY_SEPARATOR + tableName + CacheConstants.CACHE_KEY_SEPARATOR + fieldName;
        return RedisUtils.getIfAbsent(key, () -> {
            List<DictDto> fieldDict = this.fieldDictTranslatorRepository.getFieldDict(appId, tableName, fieldName, filterDisplay);
            // 缓存30天
            RedisUtils.set(key, fieldDict, CACHE_DAYS);
            return fieldDict;
        });
    }
}
