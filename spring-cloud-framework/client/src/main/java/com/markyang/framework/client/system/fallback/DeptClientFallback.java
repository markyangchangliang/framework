package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.DeptClient;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Client熔断类
 * @author yangchangliang
 */
@Component
public class DeptClientFallback implements DeptClient {

    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    @Override
    public ResultVo<List<DictDto>> getDepts(String orgId, String type) {
        return ResultVo.error("部门服务连接异常");
    }

    /**
     * 根据id获取数据
     *
     * @param id id
     * @return 对象
     */
    @Override
    public ResultVo<Dept> get(String id) {
        return ResultVo.error("部门服务连接异常");
    }
}
