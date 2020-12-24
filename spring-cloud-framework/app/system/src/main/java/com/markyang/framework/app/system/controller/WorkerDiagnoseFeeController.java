package com.markyang.framework.app.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.markyang.framework.app.base.annotation.CacheName;
import com.markyang.framework.app.system.form.search.WorkerDiagnoseFeeSearchForm;
import com.markyang.framework.app.system.form.update.WorkerDiagnoseFeeUpdateForm;
import com.markyang.framework.app.system.service.WorkerDiagnoseFeeService;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.entity.system.WorkerDiagnoseFee;
import com.markyang.framework.pojo.web.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 职员诊察费用(WorkerDiagnoseFee)控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020-03-26 16:20:27
 */
@Api(tags = "职员诊察费用控制器")
@ApiSort(1)
@CacheName("workerDiagnoseFee")
@RestController
@RequestMapping("/workerDiagnoseFee")
@AllArgsConstructor
@Slf4j
public class WorkerDiagnoseFeeController extends AbstractSystemController<WorkerDiagnoseFee, WorkerDiagnoseFeeService, WorkerDiagnoseFeeSearchForm, WorkerDiagnoseFeeUpdateForm> {

    private final WorkerDiagnoseFeeService workerDiagnoseFeeService;

    @Override
    public boolean isOrgBelonged() {
        return true;
    }

    /**
     * 同步当前职称信息
     * @return 职员部门信息
     */
    @ApiOperation(value = "同步当前职称信息")
    @PostMapping(value = "/synchronization", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> synchronization() {
        this.workerDiagnoseFeeService.synchronization();
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP);
    }

}