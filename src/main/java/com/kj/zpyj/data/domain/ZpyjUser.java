package com.kj.zpyj.data.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kj.zpyj.data.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * @author nick
 * @date 2024/7/20
 */
@TableName("user")
public class ZpyjUser {
    @TableId
    private int id;
    private BigDecimal growth;
    public void addGrowth(BigDecimal addGrowth){
        this.growth= BigDecimalUtil.add(this.growth,addGrowth);
    }
}
