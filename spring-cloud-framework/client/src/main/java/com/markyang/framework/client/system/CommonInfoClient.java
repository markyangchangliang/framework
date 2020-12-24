package com.markyang.framework.client.system;

import com.markyang.framework.client.system.fallback.CommonInfoClientFallback;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Client访问接口
 * @author yangchangliang
 */
@FeignClient(name = "system", fallback = CommonInfoClientFallback.class)
public interface CommonInfoClient {

    /**
     * 获取所有的机构信息
     * @return 机构信息
     */
    @GetMapping("/commonInfo/orgInfo")
    List<OrgInfoDto> getOrgInfo();
}
