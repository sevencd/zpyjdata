package com.kj.zpyj.data.domain;

/**
 * 订单类型
 * @author csz
 * @since 2023/11/23
 */
public enum OrderType {
    RETAIL(0, "线上零售"),
    DISH(1, "快餐"),
    PREFABRICATED(2, "净菜"),
    PREFERENCE(3, "优选"),
    WEEKEND_GROUP_BUY(4, "周末团购"),
    BRAND_PURCHASE_PROXY(5, "品牌代购"),
    OUTLINE_RETAIL(6, "线下零售");


    private int code;
    private String desc;

    OrderType(int code, String desc) {
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
