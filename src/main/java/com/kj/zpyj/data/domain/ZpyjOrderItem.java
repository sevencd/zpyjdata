package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kj.zpyj.data.util.BigDecimalUtil;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("order_item")
@Getter
public class ZpyjOrderItem {
    private String payId;
    private String orderId;
    private Integer goodsProfileId;
    private String skuId;
    private Integer barcodeId;
    private Integer goodsId;
    private String skuName;
    private String skuImage;
    private BigDecimal skuPrice;
    private BigDecimal count;
    private String unitName;
    private String remark;
    public static ZpyjOrderItem createOrderItem(String payId,String orderId,MmRetailOrderItem mmRetailOrderItem){
        ZpyjOrderItem orderItem = new ZpyjOrderItem();
        orderItem.payId=payId;
        orderItem.orderId=orderId;
        orderItem.skuId=mmRetailOrderItem.getOnlyCoding();
        orderItem.barcodeId=mmRetailOrderItem.getBarcodeId();;
        orderItem.skuName=mmRetailOrderItem.getGoodsTitle();
        orderItem.skuImage="http://miaomai.zhenpinyijia.com"+mmRetailOrderItem.getImgsOriginal();
        orderItem.skuPrice= BigDecimalUtil.getFenPrice(mmRetailOrderItem.getRetailPrice());
        orderItem.count=mmRetailOrderItem.getXnum();
        orderItem.unitName=mmRetailOrderItem.getUnitName();
        return orderItem;
    }

}
