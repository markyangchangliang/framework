package com.markyang.framework.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 经纬度计算工具类
 * @author yangchangliang
 */
@Slf4j
public final class LocationUtils {

    /**
     * 地球半径
     */
    private static final BigDecimal EARTH_RADIUS = BigDecimal.valueOf(6378137);
    /**
     * 圆周率
     */
    private static final BigDecimal RAD = BigDecimal.valueOf(Math.PI / 180.0);


    /**
     * 计算根据一个中心点，获取周围经纬度的范围大小
     * @param latitude 纬度
     * @param longitude 经度
     * @param radius 半径（米）
     * @return 范围对象
     */
    public static LocationRange computeAroundLocation(BigDecimal longitude, BigDecimal latitude, BigDecimal radius) {
        BigDecimal degree = BigDecimal.valueOf(24901 * 1609).divide(BigDecimal.valueOf(360), 6, RoundingMode.HALF_UP);
        BigDecimal dpmLat = BigDecimal.ONE.divide(degree, 6, RoundingMode.HALF_UP);
        BigDecimal radiusLat = dpmLat.multiply(radius);
        BigDecimal minLat = latitude.subtract(radiusLat);
        BigDecimal maxLat = latitude.add(radiusLat);
        BigDecimal mpdLng = degree.multiply(BigDecimal.valueOf(Math.cos(latitude.multiply(RAD).doubleValue())));
        BigDecimal dpmLng = BigDecimal.ONE.divide(mpdLng, 6, RoundingMode.HALF_UP);
        BigDecimal radiusLng = dpmLng.multiply(radius);
        BigDecimal minLng = longitude.subtract(radiusLng);
        BigDecimal maxLng = longitude.add(radiusLng);
        return new LocationRange(maxLng, minLng, maxLat, minLat);
    }


    /**
     * 计算两个经纬度之间的距离
     * @param startLng 起点经度
     * @param startLat 起点纬度
     * @param endLng 终点经度
     * @param endLat 终点纬度
     * @return 距离（米）
     */
    public static BigDecimal computeDistance(BigDecimal startLng, BigDecimal startLat, BigDecimal endLng, BigDecimal endLat)
    {
        BigDecimal startRadLat = startLat.multiply(RAD);
        BigDecimal endRadLat = endLat.multiply(RAD);
        BigDecimal a = startRadLat.subtract(endRadLat);
        BigDecimal b = (startLng.subtract(endLng)).multiply(RAD);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a.divide(BigDecimal.valueOf(2), 6, RoundingMode.HALF_UP).doubleValue()), 2) +
            Math.cos(startRadLat.doubleValue()) * Math.cos(endRadLat.doubleValue()) * Math.pow(Math.sin(b.divide(BigDecimal.valueOf(2), 6, RoundingMode.HALF_UP).doubleValue()), 2)));
        return BigDecimal.valueOf(Math.round(BigDecimal.valueOf(s).multiply(EARTH_RADIUS).multiply(BigDecimal.valueOf(10000)).doubleValue())).divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP);
    }

    /**
     * 位置对象
     */
    @Data
    @AllArgsConstructor
    public static class LocationRange {
        /**
         * 最大经度
         */
        private BigDecimal maxLongitude;
        /**
         * 最小经度
         */
        private BigDecimal minLongitude;
        /**
         * 最大纬度
         */
        private BigDecimal maxLatitude;
        /**
         * 最小纬度
         */
        private BigDecimal minLatitude;
    }

}
