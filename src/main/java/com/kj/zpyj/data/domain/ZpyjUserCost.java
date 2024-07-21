package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@Getter
@TableName("user_cost")
public class ZpyjUserCost {
    private int userId;
    private int costType;
    private String costDesc;
    private LocalDateTime costTime;
    private BigDecimal cost;
    public static ZpyjUserCost of(int userId, BigDecimal costMoney, LocalDateTime costTime, CostType costType) {
        ZpyjUserCost userCost = new ZpyjUserCost();
        userCost.userId = userId;
        userCost.costTime = costTime;
        userCost.costType = costType.getCode();
        userCost.costDesc = costType.getDesc();
        userCost.cost = costMoney;
        return userCost;
    }
}
