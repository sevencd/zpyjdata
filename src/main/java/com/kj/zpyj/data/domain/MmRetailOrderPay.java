package com.kj.zpyj.data.domain;

/**
 * @author nick
 * @date 2024/7/20
 */

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MmRetailOrderPay {
    private int pay_method;
    private BigDecimal pay_money;
    private String pay_code;
    private String pay_serial_no;
}
