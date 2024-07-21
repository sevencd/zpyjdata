package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("user_growth_record")
@Getter
public class ZpyjUserGrowthRecord {
    private int userId;
    private BigDecimal growth;

    private String changeReason;
    private LocalDateTime changeTime;

    public static ZpyjUserGrowthRecord of(int userId, BigDecimal growth, String changeReason,LocalDateTime growthTime) {
        ZpyjUserGrowthRecord userGrowthRecord = new ZpyjUserGrowthRecord();
        userGrowthRecord.userId = userId;
        userGrowthRecord.growth = growth;
        userGrowthRecord.changeReason = changeReason;
        userGrowthRecord.changeTime = growthTime;
        return userGrowthRecord;
    }
}

