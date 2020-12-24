package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.DeptClientFallback;
import com.markyang.framework.pojo.dto.system.DictDto;
import com.markyang.framework.pojo.entity.system.Dept;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Client访问接口
 * @author yangchangliang
 */
@FeignClient(name = "system", fallback = DeptClientFallback.class)
public interface DeptClient {

    /**
     * 获取部门列表
     *
     * @param orgId 机构编号
     * @param type  科室类别
     * @return 结果分页对象
     */
    @GetMapping("/dept/depts")
    ResultVo<List<DictDto>> getDepts(@RequestParam(required = false, value = "orgId") String orgId,
                                     @RequestParam(required = false, value = "type") String type);

    /**
     * 根据id获取数据
     * @param id id
     * @return 对象
     */
    @GetMapping("/dept/{id}")
    ResultVo<Dept> get(@PathVariable String id);
}
