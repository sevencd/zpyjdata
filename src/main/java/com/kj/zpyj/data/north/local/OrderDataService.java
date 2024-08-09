package com.kj.zpyj.data.north.local;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kj.zpyj.data.domain.*;
import com.kj.zpyj.data.south.adapter.repository.*;
import com.kj.zpyj.data.util.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
        List<ZpyjOrdersReady> zpyjOrders = zpyjOrderMapper.selectOrdersReady();
        for (ZpyjOrdersReady zpyjOrdersReady : zpyjOrders) {
            String orderId=null;
            List<ZpyjOrderItem> zpyjOrderItems = zpyjOrderItemMapper.selectList(Wrappers.<ZpyjOrderItem>lambdaQuery().eq(ZpyjOrderItem::getPayId, zpyjOrdersReady.getId()));
            if(zpyjOrderItems.size()>0){
                orderId=zpyjOrderItems.get(0).getOrderId();
            }else {
                log.info("支付单{}没有找到对应的订单", zpyjOrdersReady.getId());
            }
            ZpyjOrder zpyjOrder = getZpyjOrder(zpyjOrdersReady, orderId);
            zpyjOrderMapper.insert(zpyjOrder);

            List<MmRetailOrderPay> mmRetailOrderPays = mmRetailOrderMapper.selectMmRetailOrderPaysByOrderCode(orderId);
            for (MmRetailOrderPay mmRetailOrderPay : mmRetailOrderPays) {
                ZpyjOrderPay zpyjOrderPay = ZpyjOrderPay.createZpyjOrderPay(orderId, mmRetailOrderPay);
                zpyjOrderPayMapper.insert(zpyjOrderPay);
            }
//            ZpyjUserGrowthRecord zpyjUserGrowthRecord = ZpyjUserGrowthRecord.of(zpyjOrdersReady.getCustomerId(), payMoney, GrowthType.CONSUME.getDesc(), zpyjOrdersReady.getPlaceOrderTime());
//            ZpyjUserCost zpyjUserCost = ZpyjUserCost.of(zpyjOrdersReady.getCustomerId(), payMoney, zpyjOrdersReady.getPlaceOrderTime(), CostType.CONSUME);
//            zpyjUserGrowthRecordMapper.insert(zpyjUserGrowthRecord);
//            ZpyjUser zpyjUser = zpyjUserMapper.selectById(zpyjOrdersReady.getCustomerId());
//            zpyjUser.addGrowth(payMoney);
//            zpyjUserMapper.updateById(zpyjUser);
//            zpyjUserCostMapper.insert(zpyjUserCost);
        }
    }

    private static ZpyjOrder getZpyjOrder(ZpyjOrdersReady zpyjOrdersReady, String orderId) {
        ZpyjOrder zpyjOrder=new ZpyjOrder();
        zpyjOrder.setOrderId(orderId);
        zpyjOrder.setOrderType(OrderType.OUTLINE_RETAIL.getCode());
        zpyjOrder.setPayId(zpyjOrdersReady.getId());
        zpyjOrder.setPrice(zpyjOrdersReady.getPrice());
        zpyjOrder.setOrderState(OrderState.FINISH.name());
        zpyjOrder.setPaidAmount(zpyjOrdersReady.getPaidAmount());
        zpyjOrder.setPackageFee(zpyjOrdersReady.getPackageFee());
        zpyjOrder.setDiscountAmount(zpyjOrdersReady.getDiscountAmount());
        zpyjOrder.setFinishedTime(zpyjOrdersReady.getPayTime());
        zpyjOrder.setSubsidyAmount(zpyjOrdersReady.getSubsidyAmount());
        return zpyjOrder;
    }

}
