package com.kj.zpyj.data.domain;

/**
 * @author csz
 * @since 2023/10/31
 */
public enum OrderState {
    /**
     * 待支付
     */
    WAIT_PAY,
    /**
     * 待取货
     */
    WAIT_PICKUP,
    /**
     * 已完成
     */
    FINISH,
    /**
     * 已取消
     */
    CANCEL,
    REFUND,
    DELETE;
}
