package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author csz
 * @since 2023/10/30
 */
@TableName("orders_ready")
@Getter
@Setter
public class ZpyjOrdersReady {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String transactionId;
    /**
     * 订单类别
     */
    private int orderClass;

    /**
     * 用户id
     */
    private Integer customerId;
    /**
     * 门店id
     */
    private Integer storeId;
    /**
     * 订单总价
     */
    private BigDecimal price;
    /**
     * 订单备注
     */
    private String remark;
    /**
     * 自提时间
     */
    private String selfPickupTime;
    /**
     * 下单时间
     */
    private LocalDateTime placeOrderTime;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    private String payState;
    private BigDecimal walletFee;

    private String couponCode;
    /**
     * 打包费
     */
    private BigDecimal packageFee;
    private BigDecimal discountAmount;
    /**
     * 支付金额
     */
    private BigDecimal paidAmount;
    /**
     * 补贴金额
     */
    private BigDecimal subsidyAmount;

}
