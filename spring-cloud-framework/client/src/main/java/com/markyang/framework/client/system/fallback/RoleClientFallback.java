package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.RoleClient;
import com.markyang.framework.pojo.dto.system.RoleDto;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色Client熔断类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
public class RoleClientFallback implements RoleClient {
    /**
     * 获取用户所有的角色
     *
     * @param userId 用户ID
     * @return 结果对象
     */
    @Override
    public ResultVo<List<RoleDto>> getUserRoles(String userId) {
        return ResultVo.error("用户角色获取异常");
    }
}
