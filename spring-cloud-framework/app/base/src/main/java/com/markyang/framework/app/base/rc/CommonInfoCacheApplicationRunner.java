package com.markyang.framework.app.base.rc;

import com.markyang.framework.service.common.CommonInfoCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 公用信息缓存Runner
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(prefix = "framework", name = "common-info-cache-enabled", havingValue = "true")
public class CommonInfoCacheApplicationRunner implements ApplicationRunner {

    private final CommonInfoCacheService commonInfoCacheService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 缓存信息 先删除旧的
        log.info("开始清除缓存！！！！！！");
        this.commonInfoCacheService.deleteAllCachedOrgInfo();
        this.commonInfoCacheService.deleteAllCachedDeptInfo();
        this.commonInfoCacheService.deleteAllCachedUserWorkerInfo();
        log.info("缓存清除完成！！！！！！");
        this.commonInfoCacheService.cacheOrgInfo();
        this.commonInfoCacheService.cacheDeptInfo();
        this.commonInfoCacheService.cacheUserWorkerInfo();
        log.info("更新缓存完成！！！！！！！！！！");
    }
}
