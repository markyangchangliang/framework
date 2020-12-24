package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.OrgClient;
import com.markyang.framework.pojo.entity.system.Org;
import com.markyang.framework.pojo.web.ResultVo;
import org.springframework.stereotype.Component;

/**
 * Client熔断类
 * @author yangchangliang
 */
@Component
public class OrgClientFallback implements OrgClient {

    /**
     * 根据id获取数据
     *
     * @param id id
     * @return 对象
     */
    @Override
    public ResultVo<Org> get(String id) {
        return ResultVo.error("部门服务连接异常");
    }
}
