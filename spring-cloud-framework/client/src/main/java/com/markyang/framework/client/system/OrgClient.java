package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.OrgClientFallback;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client访问接口
 * @author yangchangliang
 */
@Service
@FeignClient(name = "system", fallback = OrgClientFallback.class)
public interface OrgClient {
    /**
     * 根据id获取数据
     * @param id id
     * @return 对象
     */
    @GetMapping("/org/{id}")
    ResultVo<Org> get(@PathVariable String id);
}
