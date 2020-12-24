package com.markyang.framework.app.system.controller;

import com.google.common.collect.Lists;
import com.markyang.framework.app.system.repository.DatabaseRepository;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.ColumnInfoDto;
import com.markyang.framework.pojo.dto.system.TableInfoDto;
import com.markyang.framework.pojo.dto.system.TableInfoTreeDto;
import com.markyang.framework.pojo.web.ResultVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 数据库控制器
 *
 * @author yangchangliang
 * @date 2020/5/29 4:27 下午 星期五
 * @version 1
 */
@RestController
@RequestMapping("/database")
@Slf4j
@AllArgsConstructor
public class DatabaseController {

    private final DatabaseRepository repository;

    /**
     * 获取表信息
     * @param databaseName 数据库名称
     * @param tableName 表名
     * @return 表信息
     */
    @GetMapping("/tableInfo/{databaseName}/{tableName}")
    public ResultVo<TableInfoDto> getTableInfo(@PathVariable String databaseName, @PathVariable String tableName) {
        TableInfoDto tableInfoDto = TableInfoDto.builder().build();
        List<ColumnInfoDto> columnInfoDtoList = this.repository.getTableColumnInfo(databaseName, tableName);
        tableInfoDto.setTableName(columnInfoDtoList.get(1).getColumn2());
        tableInfoDto.setTableColumns(columnInfoDtoList);
        columnInfoDtoList.parallelStream()
            .filter(columnInfoDto -> StringUtils.equals(columnInfoDto.getColumn0(), "comment"))
            .findFirst()
            .ifPresent(columnInfoDto -> {
                String comment = StringUtils.substringBetween(columnInfoDto.getColumn1(), "comment = '", "'");
                if (StringUtils.isNotBlank(comment)) {
                    tableInfoDto.setTableComment(comment);
                    return;
                }
                tableInfoDto.setTableComment(tableInfoDto.getTableName());
            });
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, tableInfoDto);
    }

    /**
     * 数据库所有表信息
     * @return 所有表信息
     */
    @GetMapping("/databaseTableInfos")
    public ResultVo<List<TableInfoTreeDto>> getDatabaseTableInfos() {
        List<TableInfoDto> databaseTableInfos = this.repository.getDatabaseTableInfos();
        ConcurrentMap<String, List<TableInfoDto>> concurrentMap = databaseTableInfos.parallelStream().collect(Collectors.groupingByConcurrent(TableInfoDto::getDatabaseName));
        List<TableInfoTreeDto> tableInfoTreeList = Lists.newArrayList();
        for (Map.Entry<String, List<TableInfoDto>> entry : concurrentMap.entrySet()) {
            tableInfoTreeList.add(
                TableInfoTreeDto.builder()
                .label(entry.getKey())
                .children(entry.getValue().parallelStream().peek(tableInfoDto -> tableInfoDto.setLabel(
                    StringUtils.isNotBlank(tableInfoDto.getTableComment()) ? tableInfoDto.getTableComment() : tableInfoDto.getTableName()
                )).collect(Collectors.toList()))
                .build()
            );
        }
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, tableInfoTreeList);
    }
}
