package com.kj.zpyj.data.north.local;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kj.zpyj.data.domain.*;
import com.kj.zpyj.data.south.adapter.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author nick
 * @date 2024/7/20
 */
@Service
public class OrderDataService {
    private static final Logger log = LoggerFactory.getLogger(OrderDataService.class);
    private final ZpyjOrderReadyMapper zpyjOrderReadyMapper;
    private final ZpyjOrderMapper zpyjOrderMapper;
    private final ZpyjOrderItemMapper zpyjOrderItemMapper;
    private final ZpyjOrderPayMapper zpyjOrderPayMapper;
    private final ZpyjUserGrowthRecordMapper zpyjUserGrowthRecordMapper;
    private final ZpyjUserCostMapper zpyjUserCostMapper;
    private final ZpyjUserMapper zpyjUserMapper;
    private final MmRetailOrderMapper mmRetailOrderMapper;

    public OrderDataService(ZpyjOrderReadyMapper zpyjOrderReadyMapper, ZpyjOrderMapper zpyjOrderMapper, ZpyjOrderItemMapper zpyjOrderItemMapper, ZpyjOrderPayMapper zpyjOrderPayMapper, ZpyjUserGrowthRecordMapper zpyjUserGrowthRecordMapper, ZpyjUserCostMapper zpyjUserCostMapper, ZpyjUserMapper zpyjUserMapper, MmRetailOrderMapper mmRetailOrderMapper) {
        this.zpyjOrderReadyMapper = zpyjOrderReadyMapper;
        this.zpyjOrderMapper = zpyjOrderMapper;
        this.zpyjOrderItemMapper = zpyjOrderItemMapper;
        this.zpyjOrderPayMapper = zpyjOrderPayMapper;
        this.zpyjUserGrowthRecordMapper = zpyjUserGrowthRecordMapper;
        this.zpyjUserCostMapper = zpyjUserCostMapper;
        this.zpyjUserMapper = zpyjUserMapper;
        this.mmRetailOrderMapper = mmRetailOrderMapper;
    }


    public void recoveryOrderData() {
        List<MmRetailOrder> mmRetailOrders = getMmRetailOrders();
        mmRetailOrders.forEach(mmRetailOrder -> {
            String payId = IdWorker.getIdStr();
            Integer customerId = this.getCustomerId(mmRetailOrder.getMemberId());
            ZpyjOrdersReady zpyjOrdersReady = ZpyjOrdersReady.of(payId, customerId, mmRetailOrder);
            ZpyjOrder zpyjOrder = ZpyjOrder.of(mmRetailOrder.getOrderCode(), zpyjOrdersReady);
            zpyjOrderReadyMapper.insert(zpyjOrdersReady);
            zpyjOrderMapper.insert(zpyjOrder);
            List<MmRetailOrderItem> mmRetailOrderItems = mmRetailOrderMapper.selectMmRetailOrderItemByOrderCode(mmRetailOrder.getOrderCode());
            for (MmRetailOrderItem mmRetailOrderItem : mmRetailOrderItems) {
                ZpyjOrderItem zpyjOrderItem = ZpyjOrderItem.createOrderItem(zpyjOrder.getPayId(), zpyjOrder.getOrderId(), mmRetailOrderItem);
                zpyjOrderItemMapper.insert(zpyjOrderItem);

            }
            List<MmRetailOrderPay> mmRetailOrderPays = mmRetailOrderMapper.selectMmRetailOrderPaysByOrderCode(mmRetailOrder.getOrderCode());
            for (MmRetailOrderPay mmRetailOrderPay : mmRetailOrderPays) {
                ZpyjOrderPay zpyjOrderPay = ZpyjOrderPay.createZpyjOrderPay(zpyjOrder.getOrderId(), mmRetailOrderPay);
                zpyjOrderPayMapper.insert(zpyjOrderPay);
            }
            if(Objects.isNull(customerId)){
                return;
            }
            BigDecimal payMoney = mmRetailOrder.getDiscountPrice();
            ZpyjUserGrowthRecord zpyjUserGrowthRecord = ZpyjUserGrowthRecord.of(zpyjOrdersReady.getCustomerId(), payMoney, GrowthType.CONSUME.getDesc(), zpyjOrdersReady.getPlaceOrderTime());
            ZpyjUserCost zpyjUserCost = ZpyjUserCost.of(zpyjOrdersReady.getCustomerId(), payMoney, zpyjOrdersReady.getPlaceOrderTime(), CostType.CONSUME);
            zpyjUserGrowthRecordMapper.insert(zpyjUserGrowthRecord);
            ZpyjUser zpyjUser = zpyjUserMapper.selectById(zpyjOrdersReady.getCustomerId());
            zpyjUser.addGrowth(payMoney);
            zpyjUserMapper.updateById(zpyjUser);
            zpyjUserCostMapper.insert(zpyjUserCost);
        });

    }

    private Integer getCustomerId(Integer memberId) {
        if (memberId == 0) {
            return null;
        }
        LambdaQueryWrapper<ZpyjUser> queryWrapper = Wrappers.<ZpyjUser>lambdaQuery().eq(ZpyjUser::getMmMemberId, memberId);
        return zpyjUserMapper.selectOne(queryWrapper).getId();
    }

    private List<MmRetailOrder> getMmRetailOrders() {
        LambdaQueryWrapper<MmRetailOrder> queryWrapper = Wrappers.<MmRetailOrder>lambdaQuery().in(MmRetailOrder::getOrderId, Arrays.asList(58830, 62100)).orderByAsc(MmRetailOrder::getAddtime);
        return mmRetailOrderMapper.selectList(queryWrapper);
    }


}
