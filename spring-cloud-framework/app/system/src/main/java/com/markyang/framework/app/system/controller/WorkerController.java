package com.markyang.framework.app.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.base.upload.service.UploadService;
import com.markyang.framework.app.system.form.search.WorkerSearchForm;
import com.markyang.framework.app.system.form.update.WorkerUpdateForm;
import com.markyang.framework.app.system.service.WorkerService;
import com.markyang.framework.pojo.common.support.PageDefault;
import com.markyang.framework.pojo.common.support.TreeNode;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.constant.TableConstants;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.entity.system.DeptWorker;
import com.markyang.framework.pojo.entity.system.Worker;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 职员管理(Worker)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "职员管理控制器")
@ApiSort(1)
@CacheName("worker")
@RestController
@RequestMapping("/worker")
@AllArgsConstructor
@Slf4j
public class WorkerController extends AbstractSystemController<Worker, WorkerService, WorkerSearchForm, WorkerUpdateForm> {

    private final WorkerService workerService;
    /**
     * 注入Upload上传服务类
     */
    private List<UploadService> uploadServices;

    /**
     * 文件上传接口
     *
     * @param type 文件上传类型
     * @param file 文件对象
     * @return 结果对象
     */
    @ApiOperationSupport(order = 1, author = "yangchangliang")
    @ApiOperation(value = "文件上传", notes = "上传文件接口，支持各种文件上传")
    @PostMapping(value = "/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVo<?> upload(@PathVariable String type, @RequestParam("file") MultipartFile file) {
        for (UploadService uploadService : this.uploadServices) {
            if (uploadService.support(type)) {
                Object result = uploadService.upload(file);
                return ResultVo.success("上传成功", result);
            }
        }
        return ResultVo.error("不支持的文件上传类型：" + type);
    }

    /**
     * 请求一个实体对象或者一个实体对象列表
     *
     * @param page       分页对象
     * @param searchForm 搜索对象
     * @return 一个实体对象或者一个实体对象列表
     */
    @ApiOperationSupport(order = 2, author = "yangchangliang")
    @ApiOperation(value = "获取多条数据", notes = "根据查询条件获取分页多条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页参数-页码", defaultValue = "1", paramType = "query", dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "size", value = "分页参数-分页大小", defaultValue = "20", paramType = "query", dataTypeClass = Integer.class, example = "20"),
            @ApiImplicitParam(name = "sort", value = "分页参数-排序", defaultValue = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc", paramType = "query", dataTypeClass = String.class, example = TableConstants.CREATED_DATETIME_FIELD_NAME + ",desc"),
    })
    @Cacheable(cacheNames = "#root.targetClass.getAnnotation(T(com.zxrj.framework.app.base.annotation.CacheName)).value()", unless = "!#result.isSuccess()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResultVo<PageVo<Worker>> get(Page<Worker> page, WorkerSearchForm searchForm) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.workerService.getOrgDeptWorkers(searchForm.getDeptId(), page));
    }

    /**
     * 获取机构数
     *
     * @param orgId 机构编号
     * @return 结果对象
     */
    @ApiOperationSupport(order = 3, author = "yangchangliang")
    @ApiOperation(value = "获取部门职员树")
    @GetMapping(value = "/tree", produces = "application/json;charset=UTF-8")
    public ResultVo<List<TreeNode>> getDeptWorkerTree(@RequestParam(required = true, value = "orgId") String orgId,
                                                      @RequestParam(required = false, value = "type") String type,
                                                      @RequestParam(required = false, value = "positional") String positional,
                                                      @RequestParam(required = false, value = "post") String post,
                                                      @RequestParam(required = false, value = "positionGrade") String positionGrade,
                                                      @RequestParam(required = false, value = "online") String online) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP,
                this.workerService.getDeptWorkerTree(orgId, type, positional, post, positionGrade, online));
    }

    /**
     * 获取职员部门
     *
     * @param workerId 职员编号
     * @return 职员部门信息
     */
    @ApiOperationSupport(order = 5, author = "yangchangliang")
    @ApiOperation(value = "获取职员部门")
    @GetMapping(value = "/workerDept/{workerId}", produces = "application/json;charset=UTF-8")
    public ResultVo<DeptWorker> getWorkerDept(@PathVariable String workerId) {
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, this.workerService.getWorkerDept(workerId));
    }

    /**
     * 修改职员部门
     *
     * @param deptWorker 职员部门信息
     * @return 结果
     */
    @ApiOperationSupport(order = 6, author = "yangchangliang")
    @ApiOperation(value = "修改职员部门")
    @PutMapping(value = "/updateWorkerDept", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> updateWorkerDept(@RequestBody DeptWorker deptWorker) {
        this.workerService.updateWorkerDept(deptWorker);
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }
}