package com.markyang.framework.pojo.constant;

/**
 * 框架常量定义
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/25 12:19 下午 星期三
 */
public interface FrameworkConstants {

    /**
     * 日期时间格式
     */
    String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    String TIME_PATTERN = "HH:mm:ss";

    /**
     * Api文档地址
     */
    String API_DOC_URI = "/v2/api-docs*";

    /**
     * 对外开放的Api地址
     */
    String API_URI = "/api/**";

    /**
     * 监控端点URI
     */
    String ENDPOINT_URI = "/actuator/**";

    /**
     * 允许的静态资源路径
     */
    String[] STATIC_RESOURCES_URI = {
        "/**/*.jpg",
        "/**/*.jpeg",
        "/**/*.png",
        "/**/*.gif",
        "/**/*.zip",
        "/**/*.rar",
        "/**/*.doc",
        "/**/*.docx",
        "/**/*.txt",
        "/**/*.ppt",
        "/**/*.pptx",
        "/**/*.xls",
        "/**/*.xlsx"
    };

    /**
     * 忽略的请求后缀
     */
    String[] IGNORED_REQUEST_SUFFIXES = {
        ".js",
        ".css",
        ".map",
        ".woff",
        ".ttf",
        ".jpg",
        ".jpeg",
        ".png",
        ".gif",
        ".xml",
        ".json",
        ".html",
    };

    /**
     * 应用ID
     */
    String REGIONAL_MEDICAL_APP_ID = "markyang";

    /**
     * 数据字典字段含义字段名称后缀
     */
    String FIELD_DICT_MEAN_FIELD_SUFFIX = "Name";

    /**
     * 数据传输对象命名后缀
     */
    String DATA_TRANSFER_OBJECT_CLASS_SUFFIX = "Dto";

    /**
     * 用户权限缓存KEY
     */
    String USER_AUTHORITIES_CACHE_KEY = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "userAuthorities" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 用户已经授权的请求方法和URI的映射缓存KEY
     */
    String USER_AUTHORITY_URI_METHOD_CACHE_KEY = CacheConstants.CACHE_PREFIX + CacheConstants.CACHE_KEY_SEPARATOR + "userAuthorityUriMethod" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * Redis锁key前缀
     */
    String LOCKING_KEY_PREFIX = "framework" + CacheConstants.CACHE_KEY_SEPARATOR + "locking" + CacheConstants.CACHE_KEY_SEPARATOR;

    /**
     * 逗号分隔符
     */
    String COMMA_SEPARATOR = ",";

    /**
     * 管理员默认密码
     */
    String USER_DEFAULT_PASSWORD = "123456";

    /**
     * 操作成功提示
     */
    String GENERIC_SUCCESS_TIP = "操作成功！";

    /**
     * 操作失败提示
     */
    String GENERIC_ERROR_TIP = "操作失败！";

    /**
     * 支付成功提示
     */
    String PAYMENT_SUCCESS_TIP = "支付成功！";

    /**
     * 支付失败提示
     */
    String PAYMENT_ERROR_TIP = "支付失败！";

    /**
     * 退费成功提示
     */
    String REFUND_SUCCESS_TIP = "退款申请成功，预计7个工作日内到账！";

    /**
     * 退费失败提示
     */
    String REFUND_ERROR_TIP = "退费失败！";

    /**
     * 升序排序
     */
    String ORDER_ASC = "asc";

    /**
     * 降序排序
     */
    String ORDER_DESC = "desc";

    /**
     * HIS业务通道消息队列名称
     */
    String RABBITMQ_QUEUE_CHANNEL = "channel";

    /**
     * 检查检验预约消息队列名称
     */
    String RABBITMQ_QUEUE_RESERVATION = "reservation";

    /**
     * 检查检验排队消息队列名称
     */
    String RABBITMQ_QUEUE_QUEUING = "queuing";

    /**
     * 最顶级的主键值
     */
    String SUPER_ID = "1";

}
