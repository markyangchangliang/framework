package com.markyang.framework.client.system.fallback;

import com.markyang.framework.client.system.CommonInfoClient;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Client熔断类
 * @author yangchangliang
 */
@Component
public class CommonInfoClientFallback implements CommonInfoClient {

    /**
     * 获取所有的机构信息
     *
     * @return 机构信息
     */
    @Override
    public List<OrgInfoDto> getOrgInfo() {
        return Collections.emptyList();
    }
}
