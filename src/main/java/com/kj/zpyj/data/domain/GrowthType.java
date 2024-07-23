package com.kj.zpyj.data.domain;

/**
 * 成长值类别
 * @author csz
 * @since 2024/4/22
 */
public enum GrowthType {
    CHECK_IN(1, "签到"),
    RECHARGE(2, "充值"),
    MEMBER_ACTIVITY(3, "购买礼包"),
    CONSUME(4, "消费");

    private int code;
    private String desc;

    GrowthType(int code, String desc) {
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
