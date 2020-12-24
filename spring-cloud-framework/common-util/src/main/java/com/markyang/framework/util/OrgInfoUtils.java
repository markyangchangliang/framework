package com.markyang.framework.util;

import com.markyang.framework.pojo.common.support.InfoEntry;
import com.markyang.framework.pojo.constant.CacheConstants;
import com.markyang.framework.pojo.constant.FrameworkConstants;
import com.markyang.framework.pojo.dto.system.OrgConfigDto;
import com.markyang.framework.pojo.dto.system.OrgInfoDto;
import com.markyang.framework.pojo.web.ResultVo;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 机构信息工具类
 *
 * @author yangchangliang
 * @version 1
 */
public final class OrgInfoUtils {

    /**
     * OrgInfo缓存键
     */
    public static final String ORG_INFO_CACHE_KEY = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + CacheConstants.COMMON_INFO_CACHE_KEY_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "orgInfo" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 获取机构信息
     *
     * @param orgId 机构ID
     * @return 机构信息Optional对象
     */
    public static Optional<OrgInfoDto> getOrgInfo(String orgId) {
        return Optional.ofNullable(RedisUtils.get(ORG_INFO_CACHE_KEY + orgId));
    }

    /**
     * 获取所有的机构信息
     *
     * @return 机构信息
     */
    public static List<OrgInfoDto> getAllOrgInfo() {
        return RedisUtils.getAll(ORG_INFO_CACHE_KEY);
    }

    /**
     * 获取机构微信小程序appId
     *
     * @param orgId 机构ID
     * @return appId
     */
    public static Optional<String> getOrgWxMiniAppId(String orgId) {
        return getOrgInfo(orgId).flatMap(orgInfoDto -> {
            OrgConfigDto configDto = orgInfoDto.getConfigDto();
            if (Objects.isNull(configDto)) {
                return Optional.empty();
            }
            OrgConfigDto.WxConfig wxConfig = configDto.getWxConfig();
            if (Objects.isNull(wxConfig)) {
                return Optional.empty();
            }
            return Optional.of(wxConfig.getAppId());
        });
    }

    /**
     * 获取所有指定类型的机构
     *
     * @param type 结构类型
     * @return 机构列表
     */
    public static List<InfoEntry> getOrganizationsOfType(String type) {
        List<OrgInfoDto> orgInfoDtoList = RedisUtils.getAll(ORG_INFO_CACHE_KEY);
        return orgInfoDtoList.parallelStream()
                .filter(orgInfoDto -> StringUtils.equals(type, orgInfoDto.getType()))
                .map(orgInfoDto -> InfoEntry.of(orgInfoDto.getId(), orgInfoDto.getName(), null, orgInfoDto.getLogo()))
                .collect(Collectors.toList());
    }

    /**
     * 获取所有指定类型的机构并且根据提供经纬度距离进行排序
     *
     * @param type 结构类型
     * @return 机构列表
     */
    public static List<InfoEntry> getOrderedOrganizationsOfType(String type, BigDecimal longitude, BigDecimal latitude) {
        List<OrgInfoDto> orgInfoDtoList = RedisUtils.getAll(ORG_INFO_CACHE_KEY);
        return orgInfoDtoList.parallelStream()
                .filter(orgInfoDto -> StringUtils.equals(type, orgInfoDto.getType()))
                .map(orgInfoDto -> {
                    BigDecimal distance;
                    if (Objects.isNull(orgInfoDto.getLongitude()) || Objects.isNull(orgInfoDto.getLatitude())) {
                        // 这里机构的经度为可能为空
                        distance = BigDecimal.valueOf(10000000);
                    } else {
                        distance = LocationUtils.computeDistance(longitude, latitude, orgInfoDto.getLongitude(), orgInfoDto.getLatitude());
                    }
                    return InfoEntry.of(orgInfoDto.getId(), orgInfoDto.getName(), distance.intValue(), orgInfoDto.getLogo());
                })
                .sorted(Comparator.comparingInt(InfoEntry::getSeq))
                .collect(Collectors.toList());
    }

    /**
     * 获取机构配置信息
     *
     * @param orgId 机构编号
     * @return 结果对象
     */
    public static ResultVo<OrgInfoDto> getOrgInfoSafely(String orgId) {
        // 请求url
        Optional<OrgInfoDto> orgInfoDtoOptional = getOrgInfo(orgId);
        if (!orgInfoDtoOptional.isPresent()) {
            return ResultVo.error(String.format("未获取到机构[%s]缓存信息！", orgId));
        }
        OrgInfoDto orgInfoDto = orgInfoDtoOptional.get();
        if (StringUtils.isBlank(orgInfoDto.getExchangeUrl())) {
            return ResultVo.error(String.format("机构[%s]交换程序URL地址信息不存在！", orgInfoDto.getName()));
        }
        if (StringUtils.isBlank(orgInfoDto.getHisWorker())) {
            return ResultVo.error(String.format("没有配置机构[%s]的默认操作员信息！", orgInfoDto.getName()));
        }
        if (StringUtils.isBlank(orgInfoDto.getHisWorkerPwd()) || StringUtils.isBlank(orgInfoDto.getHisDeptId())) {
            return ResultVo.error("机构[" + orgInfoDto.getName() + "]的默认操作员信息[" + orgInfoDto.getHisWorker() + "]配置错误！");
        }
        return ResultVo.success(FrameworkConstants.GENERIC_SUCCESS_TIP, orgInfoDto);
    }
}
