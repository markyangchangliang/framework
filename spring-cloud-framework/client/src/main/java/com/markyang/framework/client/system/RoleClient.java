package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.RoleClientFallback;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 角色Client访问接口
 *
 * @author yangchangliang
 * @version 1
 */
@Service
@FeignClient(name = "system", fallback = RoleClientFallback.class)
public interface RoleClient {

    /**
     * 获取机构所有的用户
     * @param userId 用户ID
     * @return 结果对象
     */
    @GetMapping("/role/userRoles/{userId}")
    ResultVo<List<RoleDto>> getUserRoles(@PathVariable String userId);
}
