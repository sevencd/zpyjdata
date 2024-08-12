package com.kj.zpyj.data.domain;

/**
 * @author nick
 * @date 2024/7/20
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.math.BigDecimal;
@TableName("retail_order")
@Getter
public class MmRetailOrder {
    @TableId("order_id")
    private Integer orderId;
    private Integer storesId;
    private String orderCode;
    private BigDecimal total;
    private BigDecimal discountPrice;
    private Integer addtime;
    private Integer payTime;
    private String mobile;
    private Integer memberId;

    public static void main(String[] args) {
        String code="123_10050005";
//        String code="10050005";
        String[] arr=code.split("_");
        if(arr.length>1){
            System.out.println(arr[1]);
        }else {
            System.out.println(code);
        }
    }
}
