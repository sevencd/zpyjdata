package com.kj.zpyj.data.domain;

/**
 * @author nick
 * @date 2024/7/20
 */

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MmRetailOrderItem {
    private String orderCode;
    private String goodsTitle;
    private String onlyCoding;
    private String imgsOriginal;
    private int barcodeId;
    private BigDecimal retailPrice;
    private BigDecimal xnum;
    private String unitName;
}
