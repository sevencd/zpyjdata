package com.kj.zpyj.data.north.local;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kj.zpyj.data.domain.*;
import com.kj.zpyj.data.south.adapter.repository.*;
import com.kj.zpyj.data.util.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author nick
 * @date 2024/7/20
 */
@Service
public class OrderDataService {
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

    /**
     * 补充订单商品和支付信息
     */
    public void recoveryOrderData() {
        //找出时间范围内没有订单商品和支付信息的订单
        List<ZpyjOrder> zpyjOrders = zpyjOrderMapper.selectOrders();
        //循环每一个订单，去淼迈查找对应的零售单商品和支付信息
        //把商品和支付信息依次添加到zpyj数据库，并增加成长值和消费记录
        for (ZpyjOrder zpyjOrder : zpyjOrders) {
            BigDecimal payMoney = BigDecimal.ZERO;

            List<MmRetailOrderItem> mmRetailOrderItems = mmRetailOrderMapper.selectMmRetailOrderItemByOrderCode(zpyjOrder.getOrderId());
            for (MmRetailOrderItem mmRetailOrderItem : mmRetailOrderItems) {
                ZpyjOrderItem zpyjOrderItem = ZpyjOrderItem.createOrderItem(zpyjOrder.getPayId(), zpyjOrder.getOrderId(), mmRetailOrderItem);
                zpyjOrderItemMapper.insert(zpyjOrderItem);

            }
            List<MmRetailOrderPay> mmRetailOrderPays = mmRetailOrderMapper.selectMmRetailOrderPaysByOrderCode(zpyjOrder.getOrderId());
            for (MmRetailOrderPay mmRetailOrderPay : mmRetailOrderPays) {
                payMoney = mmRetailOrderPay.getPayMoney();
                ZpyjOrderPay zpyjOrderPay = ZpyjOrderPay.createZpyjOrderPay(zpyjOrder.getOrderId(), mmRetailOrderPay);
                zpyjOrderPayMapper.insert(zpyjOrderPay);
            }
            ZpyjUserGrowthRecord zpyjUserGrowthRecord = ZpyjUserGrowthRecord.of(zpyjOrder.getCustomerId(), payMoney, GrowthType.CONSUME.getDesc(), zpyjOrder.getPlaceOrderTime());
            ZpyjUserCost zpyjUserCost = ZpyjUserCost.of(zpyjOrder.getCustomerId(), payMoney, zpyjOrder.getPlaceOrderTime(), CostType.CONSUME);
            zpyjUserGrowthRecordMapper.insert(zpyjUserGrowthRecord);
            ZpyjUser zpyjUser = zpyjUserMapper.selectById(zpyjOrder.getCustomerId());
            zpyjUser.addGrowth(payMoney);
            zpyjUserMapper.updateById(zpyjUser);
            zpyjUserCostMapper.insert(zpyjUserCost);
        }
    }

    /**
     * 恢复商品数量
     */
    public void recoveryOrderItemData() {
        //找出时间范围内没有订单商品和支付信息的订单
        List<ZpyjOrderItem> zpyjOrders = zpyjOrderMapper.selectOrderItems();
        //循环每一个订单，去淼迈查找对应的零售单商品和支付信息
        //把商品和支付信息依次添加到zpyj数据库，并增加成长值和消费记录
        for (ZpyjOrderItem zpyjOrderItem : zpyjOrders) {
            List<MmRetailOrderItem> mmRetailOrderItems = mmRetailOrderMapper.selectMmRetailOrderItemByOrderCode(zpyjOrderItem.getOrderId());
            for (MmRetailOrderItem mmRetailOrderItem : mmRetailOrderItems) {
                LambdaUpdateWrapper<ZpyjOrderItem> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
                lambdaUpdateWrapper.eq(ZpyjOrderItem::getOrderId, zpyjOrderItem.getOrderId());
                lambdaUpdateWrapper.eq(ZpyjOrderItem::getSkuId, mmRetailOrderItem.getOnlyCoding());
                lambdaUpdateWrapper.set(ZpyjOrderItem::getCount, mmRetailOrderItem.getXnum());
                zpyjOrderItemMapper.update(null, lambdaUpdateWrapper);
            }

        }
    }
    public void recoveryOrderDiscountData() {
        //找出优惠数据不对的订单
        List<ZpyjOrdersReady> zpyjOrdersReadies = zpyjOrderMapper.selectOrdersDiscount();
        for (ZpyjOrdersReady zpyjOrdersReady : zpyjOrdersReadies){
            BigDecimal discountAmount = zpyjOrdersReady.getPaidAmount();
            BigDecimal paidAmount = zpyjOrdersReady.getWalletFee();
            //更新订单实付金额和优惠金额
            LambdaUpdateWrapper<ZpyjOrdersReady> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
            lambdaUpdateWrapper.eq(ZpyjOrdersReady::getId, zpyjOrdersReady.getId());
            lambdaUpdateWrapper.set(ZpyjOrdersReady::getPaidAmount, paidAmount);
            lambdaUpdateWrapper.set(ZpyjOrdersReady::getDiscountAmount, discountAmount);
            zpyjOrderReadyMapper.update(null, lambdaUpdateWrapper);
        }
        List<ZpyjOrder> zpyjOrders = zpyjOrderMapper.selectOrders2();
        for(ZpyjOrder zpyjOrder : zpyjOrders){
            BigDecimal discountAmount = zpyjOrder.getPaidAmount();
            BigDecimal paidAmount = zpyjOrder.getWalletFee();
            //更新订单实付金额和优惠金额
            LambdaUpdateWrapper<ZpyjOrder> lambdaUpdateWrapper2 = Wrappers.lambdaUpdate();
            lambdaUpdateWrapper2.eq(ZpyjOrder::getOrderId, zpyjOrder.getOrderId());
            lambdaUpdateWrapper2.set(ZpyjOrder::getPaidAmount, paidAmount);
            lambdaUpdateWrapper2.set(ZpyjOrder::getDiscountAmount, discountAmount);
            zpyjOrderMapper.update(null, lambdaUpdateWrapper2);
            //更新商品价格
            List<MmRetailOrderItem> mmRetailOrderItems = mmRetailOrderMapper.selectMmRetailOrderItemByOrderCode(zpyjOrder.getOrderId());
            for (MmRetailOrderItem mmRetailOrderItem : mmRetailOrderItems) {
                LambdaUpdateWrapper<ZpyjOrderItem> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
                lambdaUpdateWrapper.eq(ZpyjOrderItem::getOrderId, zpyjOrder.getOrderId());
                lambdaUpdateWrapper.eq(ZpyjOrderItem::getSkuId, mmRetailOrderItem.getOnlyCoding());
                lambdaUpdateWrapper.set(ZpyjOrderItem::getSkuPrice, BigDecimalUtil.getFenPrice(mmRetailOrderItem.getRetailPrice()));
                zpyjOrderItemMapper.update(null, lambdaUpdateWrapper);
            }
        }
    }
}
