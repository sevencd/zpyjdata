package com.kj.zpyj.data.north.local;

import com.kj.zpyj.data.domain.*;
import com.kj.zpyj.data.south.adapter.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nick
 * @date 2024/7/20
 */
@Service
public class OrderDataService {
    private final ZpyjOrderMapper zpyjOrderMapper;
    private final ZpyjOrderItemMapper zpyjOrderItemMapper;
    private final ZpyjOrderPayMapper zpyjOrderPayMapper;
    private final ZpyjUserGrowthRecordMapper zpyjUserGrowthRecordMapper;
    private final ZpyjUserCostMapper zpyjUserCostMapper;
    private final ZpyjUserMapper zpyjUserMapper;
    private final MmRetailOrderMapper mmRetailOrderMapper;

    public OrderDataService(ZpyjOrderMapper zpyjOrderMapper, ZpyjOrderItemMapper zpyjOrderItemMapper, ZpyjOrderPayMapper zpyjOrderPayMapper, ZpyjUserGrowthRecordMapper zpyjUserGrowthRecordMapper, ZpyjUserCostMapper zpyjUserCostMapper, ZpyjUserMapper zpyjUserMapper, MmRetailOrderMapper mmRetailOrderMapper) {
        this.zpyjOrderMapper = zpyjOrderMapper;
        this.zpyjOrderItemMapper = zpyjOrderItemMapper;
        this.zpyjOrderPayMapper = zpyjOrderPayMapper;
        this.zpyjUserGrowthRecordMapper = zpyjUserGrowthRecordMapper;
        this.zpyjUserCostMapper = zpyjUserCostMapper;
        this.zpyjUserMapper = zpyjUserMapper;
        this.mmRetailOrderMapper = mmRetailOrderMapper;
    }
    /**
     * 补充订单商品和支付信息
     */
    public void recoveryOrderData(){
        //找出时间范围内没有订单商品和支付信息的订单
        List<ZpyjOrder> zpyjOrders=zpyjOrderMapper.selectOrders();
        //循环每一个订单，去淼迈查找对应的零售单商品和支付信息
        //把商品和支付信息依次添加到zpyj数据库，并增加成长值和消费记录
        for (ZpyjOrder zpyjOrder:zpyjOrders){
            BigDecimal payMoney = BigDecimal.ZERO;

            List<MmRetailOrderItem> mmRetailOrderItems =mmRetailOrderMapper.selectMmRetailOrderItemByOrderCode(zpyjOrder.getOrderId());
            for(MmRetailOrderItem mmRetailOrderItem:mmRetailOrderItems){
                ZpyjOrderItem zpyjOrderItem=ZpyjOrderItem.createOrderItem(zpyjOrder.getPayId(),zpyjOrder.getOrderId(),mmRetailOrderItem);
                zpyjOrderItemMapper.insert(zpyjOrderItem    );

            }
            List<MmRetailOrderPay> mmRetailOrderPays =mmRetailOrderMapper.selectMmRetailOrderPaysByOrderCode(zpyjOrder.getOrderId());
            for(MmRetailOrderPay mmRetailOrderPay:mmRetailOrderPays){
                payMoney=mmRetailOrderPay.getPay_money();
                ZpyjOrderPay zpyjOrderPay=ZpyjOrderPay.createZpyjOrderPay(zpyjOrder.getOrderId(),mmRetailOrderPay);
                zpyjOrderPayMapper.insert(zpyjOrderPay);
            }
            ZpyjUserGrowthRecord zpyjUserGrowthRecord=ZpyjUserGrowthRecord.of(zpyjOrder.getCustomerId(),payMoney,CostType.CONSUME.getDesc(), LocalDateTime.now());
            ZpyjUserCost zpyjUserCost=ZpyjUserCost.of(zpyjOrder.getCustomerId(),payMoney,LocalDateTime.now(),CostType.CONSUME);
            zpyjUserGrowthRecordMapper.insert(zpyjUserGrowthRecord);
            ZpyjUser zpyjUser=zpyjUserMapper.selectById(zpyjOrder.getCustomerId());
            zpyjUser.addGrowth(payMoney);
            zpyjUserMapper.updateById(zpyjUser);
            zpyjUserCostMapper.insert(zpyjUserCost);
        }

    }
}
