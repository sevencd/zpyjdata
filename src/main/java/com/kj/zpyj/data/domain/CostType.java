package com.kj.zpyj.data.domain;

/**
 * 花费类别
 * @author csz
 * @since 2024/4/22
 */
public enum CostType {
    RECHARGE(1, "充值"),
    MEMBER_ACTIVITY(2, "购买礼包"),
    CONSUME(3, "购买商品");

    private int code;
    private String desc;

    CostType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }
}
