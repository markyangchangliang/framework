package com.markyang.framework.service.wx;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.markyang.framework.service.exception.CommonServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 复合微信支付服务类
 *
 * @author yangchangliang
 * @version 1
 */
@AllArgsConstructor
public class CompositeWxPayService {

    /**
     * 微信支付服务类集合
     */
    private final Map<String, WxPayService> wxPayServiceMap;

    /**
     * 获取微信支付服务类
     * @param orgId 机构ID
     * @return 微信支付服务类
     */
    private WxPayService getWxPayService(String orgId) {
        WxPayService wxPayService = this.wxPayServiceMap.get(orgId);
        if (Objects.isNull(wxPayService)) {
            throw new CommonServiceException("微信支付服务类[" + orgId + "]不存在");
        }
        return wxPayService;
    }

    /**
     * 获取微信支付服务类
     * @return 微信支付服务类
     */
    private WxPayService getWxPayService() {
        if (MapUtils.isEmpty(this.wxPayServiceMap)) {
            throw new CommonServiceException("请提供至少一个微信支付服务类");
        }
        return this.wxPayServiceMap.values().iterator().next();
    }

    /**
     * <pre>
     * 查询订单.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
     * </pre>
     *
     * @param orgId 机构ID
     * @param transactionId 微信订单号
     * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
     * @return the wx pay order query result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderQueryResult queryOrder(String orgId, String transactionId, String outTradeNo) throws WxPayException {
        return this.getWxPayService(orgId).queryOrder(transactionId, outTradeNo);
    }

    /**
     * <pre>
     * 查询订单（适合于需要自定义子商户号和子商户appid的情形）.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用被扫支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
     * </pre>
     * @param orgId 机构ID
     * @param request 查询订单请求对象
     * @return the wx pay order query result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderQueryResult queryOrder(String orgId, WxPayOrderQueryRequest request) throws WxPayException {
        return this.getWxPayService(orgId).queryOrder(request);
    }

    /**
     * <pre>
     * 关闭订单.
     * 应用场景
     * 以下情况需要调用关单接口：
     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
     * 是否需要证书：   不需要。
     * </pre>
     *
     * @param orgId 机构ID
     * @param outTradeNo 商户系统内部的订单号
     * @return the wx pay order close result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderCloseResult closeOrder(String orgId, String outTradeNo) throws WxPayException {
        return this.getWxPayService(orgId).closeOrder(outTradeNo);
    }

    /**
     * <pre>
     * 关闭订单（适合于需要自定义子商户号和子商户appid的情形）.
     * 应用场景
     * 以下情况需要调用关单接口：
     * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
     * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
     * 是否需要证书：   不需要。
     * </pre>
     *
     * @param orgId 机构ID
     * @param request 关闭订单请求对象
     * @return the wx pay order close result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderCloseResult closeOrder(String orgId, WxPayOrderCloseRequest request) throws WxPayException {
        return this.getWxPayService(orgId).closeOrder(request);
    }

    /**
     * 调用统一下单接口，并组装生成支付所需参数对象.
     *
     * @param orgId 机构ID
     * @param request 统一下单请求参数
     * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
     * @throws WxPayException the wx pay exception
     */
    public <T> T createOrder(String orgId, WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.getWxPayService(orgId).createOrder(request);
    }

    /**
     * 统一下单(详见https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1)
     * 在发起微信支付前，需要调用统一下单接口，获取"预支付交易会话标识"
     * 接口地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     *
     * @param orgId 机构ID
     * @param request 请求对象，注意一些参数如appid、mchid等不用设置，方法内会自动从配置对象中获取到（前提是对应配置中已经设置）
     * @return the wx pay unified order result
     * @throws WxPayException the wx pay exception
     */
    public WxPayUnifiedOrderResult unifiedOrder(String orgId, WxPayUnifiedOrderRequest request) throws WxPayException {
        return this.getWxPayService(orgId).unifiedOrder(request);
    }

    /**
     * <pre>
     * 微信支付-申请退款.
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
     * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
     * </pre>
     *
     * @param orgId 机构ID
     * @param request 请求对象
     * @return 退款操作结果 wx pay refund result
     * @throws WxPayException the wx pay exception
     */
    public WxPayRefundResult refund(String orgId, WxPayRefundRequest request) throws WxPayException {
        return this.getWxPayService(orgId).refund(request);
    }

    /**
     * <pre>
     * 微信支付-查询退款.
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     * 以下四个参数四选一
     *
     * @param orgId 机构ID
     * @param transactionId 微信订单号
     * @param outTradeNo    商户订单号
     * @param outRefundNo   商户退款单号
     * @param refundId      微信退款单号
     * @return 退款信息 wx pay refund query result
     * @throws WxPayException the wx pay exception
     */
    public WxPayRefundQueryResult refundQuery(String orgId, String transactionId, String outTradeNo, String outRefundNo, String refundId) throws WxPayException {
        return this.getWxPayService(orgId).refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
    }

    /**
     * <pre>
     * 微信支付-查询退款（适合于需要自定义子商户号和子商户appid的情形）.
     * 应用场景：
     *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
     *  银行卡支付的退款3个工作日后重新查询退款状态。
     * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
     * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
     * </pre>
     *
     * @param orgId 机构ID
     * @param request 微信退款单号
     * @return 退款信息 wx pay refund query result
     * @throws WxPayException the wx pay exception
     */
    public WxPayRefundQueryResult refundQuery(String orgId, WxPayRefundQueryRequest request) throws WxPayException {
        return this.getWxPayService(orgId).refundQuery(request);
    }

    /**
     * 解析支付结果通知.
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     *
     * @param xmlData the xml data
     * @return the wx pay order notify result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException {
        return this.getWxPayService().parseOrderNotifyResult(xmlData);
    }

    /**
     * 解析退款结果通知
     * 详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_16&index=9
     *
     * @param xmlData the xml data
     * @return the wx pay refund notify result
     * @throws WxPayException the wx pay exception
     */
    public WxPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws WxPayException {
        return this.getWxPayService().parseRefundNotifyResult(xmlData);
    }

    /**
     * <pre>
     * 撤销订单API.
     * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
     * 应用场景：
     *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；
     *  如果用户支付成功，微信支付系统会将此订单资金退还给用户。
     *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。
     *  提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     *  调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
     *  接口链接 ：https://api.mch.weixin.qq.com/secapi/pay/reverse
     *  是否需要证书：请求需要双向证书。
     * </pre>
     *
     * @param orgId 机构ID
     * @param request the request
     * @return the wx pay order reverse result
     * @throws WxPayException the wx pay exception
     */
    public WxPayOrderReverseResult reverseOrder(String orgId, WxPayOrderReverseRequest request) throws WxPayException {
        return this.getWxPayService(orgId).reverseOrder(request);
    }
}
