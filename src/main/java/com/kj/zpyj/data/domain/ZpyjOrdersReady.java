package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kj.zpyj.data.util.BigDecimalUtil;
import com.kj.zpyj.data.util.DateUtil;
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

    public static ZpyjOrdersReady of(String payId, Integer customerId, MmRetailOrder mmRetailOrder) {
        ZpyjOrdersReady zpyjOrdersReady = new ZpyjOrdersReady();
        zpyjOrdersReady.id = payId;
        zpyjOrdersReady.orderClass = 4;
        zpyjOrdersReady.customerId = customerId;
        zpyjOrdersReady.storeId = mmRetailOrder.getStoresId();
        zpyjOrdersReady.price = BigDecimalUtil.getFenPrice(mmRetailOrder.getTotal());
        zpyjOrdersReady.placeOrderTime = DateUtil.getLocalDateTime(mmRetailOrder.getAddtime());
        zpyjOrdersReady.payTime = DateUtil.getLocalDateTime(mmRetailOrder.getPayTime());
        zpyjOrdersReady.payState = "PAID";
        zpyjOrdersReady.discountAmount = BigDecimalUtil.getFenPrice(BigDecimalUtil.subtract(mmRetailOrder.getTotal(), mmRetailOrder.getDiscountPrice()));
        zpyjOrdersReady.paidAmount = BigDecimalUtil.getFenPrice(mmRetailOrder.getDiscountPrice());
        return zpyjOrdersReady;
    }
}
