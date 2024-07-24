package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("orders")
@Getter
public class ZpyjOrder {
    @TableId(value = "order_id", type = IdType.INPUT)
    private String orderId;
    private String payId;
    private int customerId;
    private LocalDateTime placeOrderTime;
    private BigDecimal walletFee;
    private BigDecimal paidAmount;
    private BigDecimal discountAmount;
}
