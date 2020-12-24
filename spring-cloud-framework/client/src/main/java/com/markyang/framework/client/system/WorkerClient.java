package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.WorkerClientFallback;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * client接口调用
 * @author yangchangliang
 */
@FeignClient(name = "system", fallback = WorkerClientFallback.class)
public interface WorkerClient {

    /**
     * 获取推荐专家
     *
     * @param orgId      机构编号
     * @param deptType   科室类别
     * @param positional 职称
     * @param page       当前页码
     * @param size       分页大小
     * @param sort       排序
     * @return 结果分页对象
     */
    @GetMapping("/worker/onlineWorkers")
    ResultVo<PageVo<WorkerInfoDto>> getOnlineWorkers(@RequestParam(required = false, value = "orgId") String orgId,
                                                     @RequestParam(required = false, value = "deptId") String deptId,
                                                     @RequestParam(required = false, value = "deptType") String deptType,
                                                     @RequestParam(required = false, value = "positional") String positional,
                                                     @RequestParam(required = false, value = "workerName") String workerName,
                                                     @RequestParam(required = false, value = "page") Long page,
                                                     @RequestParam(required = false, value = "size") Long size,
                                                     @RequestParam(required = false, value = "sort") String sort);

    /**
     * 获取推荐专家详情
     * @param workerId 职员编号
     * @return 结果对象
     */
    @GetMapping("/worker/onlineWorkerInfo")
    ResultVo<WorkerInfoDto> getOnlineWorkerInfo(@RequestParam(required = true, value = "workerId") String workerId);
}
