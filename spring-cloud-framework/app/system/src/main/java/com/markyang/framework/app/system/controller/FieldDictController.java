package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.FieldDictSearchForm;
import com.markyang.framework.app.system.form.update.FieldDictUpdateForm;
import com.markyang.framework.app.system.service.FieldDictService;
import com.markyang.framework.pojo.common.support.ItemEntry;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.FieldDict;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据字典(FieldDict)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "数据字典控制器")
@ApiSort(1)
@CacheName("fieldDict")
@RestController
@RequestMapping("/fieldDict")
@AllArgsConstructor
@Slf4j
public class FieldDictController extends AbstractSystemController<FieldDict, FieldDictService, FieldDictSearchForm, FieldDictUpdateForm> {

    private final FieldDictService fieldDictService;



    /**
     * 获取表列表
     *
     * @param appId     应用编码
     * @return 结果对象
     */
    @ApiOperation(value = "获取表列表", notes = "数据字典中所有的表")
    @GetMapping(value = "/tables/{appId}", produces = "application/json;charset=UTF-8")
    public ResultVo<List<ItemEntry>> getAllTables(@PathVariable String appId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictService.getAllTables(appId));
    }

    /**
     * 获取表字段列表
     *
     * @param appId     应用编码
     * @param tableName 表名
     * @return 结果对象
     */
    @ApiOperation(value = "获取表字段列表", notes = "数据字典表中所有的字段")
    @GetMapping(value = "/tableFields/{appId}/{tableName}", produces = "application/json;charset=UTF-8")
    public ResultVo<List<ItemEntry>> getAllTables(@PathVariable String appId, @PathVariable String tableName) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictService.getAllTableFields(appId, tableName));
    }


    /**
     * 获取表列表
     *
     * @param appId     应用编码
     * @param tableName 表名
     * @return 结果对象
     */
    @ApiOperation(value = "生成插入语句", notes = "生成插入语句")
    @GetMapping(value = "/insertSql", produces = "application/json;charset=UTF-8")
    public ResultVo<List<FieldDict>> getGeneratedInsertSql(String appId, String tableName, String fieldName, String fieldValue, String fieldMean) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.fieldDictService.getGeneratedInsertSql(appId, tableName, fieldName, fieldValue, fieldMean));
    }

    /**
     * 获取指定应用下指定表的所有字典
     *
     * @return 结果对象
     */
    @ApiOperationSupport(order = 8, author = "yangchangliang")
    @ApiOperation(value = "获取指定应用下指定表的所有字典", notes = "根据应用和表名获取其所有字典")
    @GetMapping(value = "/all/{appId}/{tableName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<Map<String, List<DictDto>>> getAllFieldDict(@PathVariable String appId, @PathVariable String
            tableName) {
        Map<String, List<DictDto>> allFieldDict = this.fieldDictService.getAllFieldDict(appId, tableName);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, allFieldDict);
    }

    /**
     * 获取指定应用下指定表的某个字段的字典
     *
     * @param appId     需获取数据字典的应用名称
     * @param tableName 需获取数据字典的表名称
     * @param fieldName 需获取数据字典的字段名称
     * @return 结果对象
     */
    @ApiOperationSupport(order = 9, author = "yangchangliang")
    @ApiOperation(value = "获取指定应用下指定表的某个字段的字典", notes = "根据应用下表中的字段获取字典")
    @GetMapping(value = "/all/{appId}/{tableName}/{fieldName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<List<DictDto>> getFieldDict(
            @ApiParam(name = "appId", value = "需获取数据字典的应用名称", required = true, example = FrameworkConstants.REGIONAL_MEDICAL_APP_ID) @PathVariable String
                    appId,
            @ApiParam(name = "tableName", value = "需获取数据字典的表名称", required = true, example = "user") @PathVariable String
                    tableName,
            @ApiParam(name = "fieldName", value = "需获取数据字典的字段名称", required = true, example = "status") @PathVariable String
                    fieldName
    ) {
        List<DictDto> fieldDict = this.fieldDictService.getFieldDict(appId, tableName, fieldName);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, fieldDict);
    }
}