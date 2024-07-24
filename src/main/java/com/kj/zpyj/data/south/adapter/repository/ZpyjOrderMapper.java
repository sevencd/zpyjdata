package com.kj.zpyj.data.south.adapter.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kj.zpyj.data.domain.ZpyjOrder;
import com.kj.zpyj.data.domain.ZpyjOrderItem;
import com.kj.zpyj.data.domain.ZpyjOrdersReady;

import java.util.List;

/**
 * @author nick
 * @date 2024/7/20
 */
public interface ZpyjOrderMapper extends BaseMapper<ZpyjOrder> {
    List<ZpyjOrder> selectOrders();
    List<ZpyjOrderItem> selectOrderItems();

    List<ZpyjOrdersReady> selectOrdersDiscount();

    List<ZpyjOrder> selectOrders2();

    List<ZpyjOrdersReady> selectOrdersReadyPaidDiscountEquals0();
    List<ZpyjOrder> selectOrdersPaidDiscountEquals0();
}
