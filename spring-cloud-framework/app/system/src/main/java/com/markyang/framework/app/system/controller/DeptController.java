package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.DeptSearchForm;
import com.markyang.framework.app.system.form.update.DeptUpdateForm;
import com.markyang.framework.app.system.service.DeptService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理(Dept)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "部门管理控制器")
@ApiSort(1)
@CacheName("dept")
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
@Slf4j
public class DeptController extends AbstractSystemController<Dept, DeptService, DeptSearchForm, DeptUpdateForm> {

    private final DeptService deptService;

    /**
     * 获取部门树
     *
     * @param orgId    机构ID
     * @param parentId 父级ID
     * @return 结果对象
     */
    @ApiOperation(value = "获取部门树")
    @GetMapping(value = "/tree/{orgId}", produces = "application/json;charset=UTF-8")
    public ResultVo<List> getDeptTree(@PathVariable String orgId,
                                      @RequestParam(name = "parentId", required = false) String parentId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.deptService.getDeptTree(orgId, parentId));
    }

    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    @ApiOperation(value = "获取部门列表")
    @GetMapping(value = "/depts", produces = "application/json;charset=UTF-8")
    public ResultVo<List<DictDto>> getDepts(@RequestParam(required = false, value = "orgId") String orgId,
                                              @RequestParam(required = false, value = "type") String type) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.deptService.getDepts(orgId, type));
    }


}