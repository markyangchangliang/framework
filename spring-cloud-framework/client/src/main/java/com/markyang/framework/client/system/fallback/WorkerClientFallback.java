package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.WorkerClient;
import com.markyang.framework.pojo.dto.system.WorkerInfoDto;
import com.markyang.framework.pojo.web.PageVo;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.stereotype.Component;

/**
 * Client熔断类
 * @author yangchangliang
 */
@Component
public class WorkerClientFallback implements WorkerClient {

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
    @Override
    public ResultVo<PageVo<WorkerInfoDto>> getOnlineWorkers(String orgId, String deptId, String deptType, String positional, String workerName, Long page, Long size, String sort) {
        return ResultVo.error("职员服务连接异常");
    }

    /**
     * 获取推荐专家详情
     *
     * @param workerId 职员编号
     * @return 结果对象
     */
    @Override
    public ResultVo<WorkerInfoDto> getOnlineWorkerInfo(String workerId) {
        return ResultVo.error("职员服务连接异常");
    }
}
