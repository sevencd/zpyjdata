package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("orders")
@Getter
@Setter
public class ZpyjOrder {
    @TableId(value = "order_id", type = IdType.INPUT)
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单类型
     *
     * @see OrderType
     */
    private Integer orderType;
    /**
     * 支付id
     */
    private String payId;
    /**
     * 淼迈订单id
     */
    private Integer mmOrderId;
    /**
     * 淼迈订单号
     */
    private String mmOrderCode;
    /**
     * 订单总价
     */
    private BigDecimal price;
    /**
     * 订单状态
     *
     * @see OrderState
     */
    private String orderState;
    private BigDecimal paidAmount;
    /**
     * 打包费
     */
    private BigDecimal packageFee;
    private BigDecimal discountAmount;
    /**
     * 订单完成时间
     */
    private LocalDateTime finishedTime;
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 补贴金额
     */
    private BigDecimal subsidyAmount;
}
