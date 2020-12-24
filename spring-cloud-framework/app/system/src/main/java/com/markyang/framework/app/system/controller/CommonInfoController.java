package com.markyang.framework.app.system.controller;

import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import com.markyang.framework.service.common.CommonInfoCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用信息控制器
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/5/20 3:03 下午 星期三
 */
@RestController
@RequestMapping("/commonInfo")
@Slf4j
@AllArgsConstructor
public class CommonInfoController {

    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * 获取机构信息
     * @return 机构信息
     */
    @GetMapping("/orgInfo")
    public List<OrgInfoDto> getOrgInfo() {
        return this.commonInfoCacheService.getOrgInfo();
    }
}
