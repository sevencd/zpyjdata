package com.kj.zpyj.data.south.adapter.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kj.zpyj.data.domain.MmRetailOrderItem;
import com.kj.zpyj.data.domain.MmRetailOrderPay;

import java.util.List;

/**
 * @author nick
 * @date 2024/7/20
 */
@DS("miaomai")
public interface MmRetailOrderMapper {
    List<MmRetailOrderItem> selectMmRetailOrderItemByOrderCode(String orderId);

    List<MmRetailOrderPay> selectMmRetailOrderPaysByOrderCode(String orderId);
}
