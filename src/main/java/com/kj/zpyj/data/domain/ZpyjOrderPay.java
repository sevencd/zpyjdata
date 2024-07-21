package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("order_pays")
public class ZpyjOrderPay {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    private String orderCode;
    private int payWay;
    private BigDecimal payMoney;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 支付单号
     */
    private String payCode;
    /**
     * 第三方支付返回的支付流水号
     */
    private String paySerialNo;
    /**
     * 使用的积分数或者优惠券id
     */
    private String pointsCoupon;


    public static ZpyjOrderPay createZpyjOrderPay(String orderId, MmRetailOrderPay mmRetailOrderPay) {
        ZpyjOrderPay zpyjOrderPay = new ZpyjOrderPay();
        zpyjOrderPay.orderCode = orderId;
        zpyjOrderPay.payWay = mmRetailOrderPay.getPay_method();
        zpyjOrderPay.payMoney = mmRetailOrderPay.getPay_money();
        zpyjOrderPay.payCode = mmRetailOrderPay.getPay_code();
        zpyjOrderPay.paySerialNo = mmRetailOrderPay.getPay_serial_no();

        return zpyjOrderPay;
    }
}
