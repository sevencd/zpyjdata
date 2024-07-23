package com.kj.zpyj.data.domain;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@Getter
public class ZpyjOrder {
    private String payId;
    private int customerId;
    private LocalDateTime placeOrderTime;
    private String orderId;
}
