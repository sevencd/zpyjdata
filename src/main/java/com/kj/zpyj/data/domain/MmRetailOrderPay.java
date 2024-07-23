package com.kj.zpyj.data.domain;

/**
 * @author nick
 * @date 2024/7/20
 */

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MmRetailOrderPay {
    private int payMethod;
    private BigDecimal payMoney;
    private String payCode;
    private String paySerialNo;
}
