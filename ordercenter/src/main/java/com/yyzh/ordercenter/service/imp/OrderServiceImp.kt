package com.yyzh.ordercenter.service.imp

import com.kotlin.order.data.protocol.*
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.baselibrary.ext.convertBoolean
import com.yyzh.ordercenter.data.api.OrderApi
import com.yyzh.ordercenter.data.req.SubmitOrderReq
import com.yyzh.ordercenter.service.OrderService
import rx.Observable
import javax.inject.Inject

class OrderServiceImp @Inject constructor():OrderService {
    override fun getOrderById(orderId: Int): Observable<Order> {
        return RetrfitFactory.instance.create(OrderApi::class.java).getOrderById(GetOrderByIdReq(orderId)).convert()
    }

    override fun submitOrder(order: Order): Observable<Boolean> {
        return RetrfitFactory.instance.create(OrderApi::class.java).submitOrder(SubmitOrderReq(order)).convertBoolean()
    }

    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return RetrfitFactory.instance.create(OrderApi::class.java).getOrderList(GetOrderListReq(orderStatus)).convert()
    }

    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return RetrfitFactory.instance.create(OrderApi::class.java).cancelOrder(CancelOrderReq(orderId)).convertBoolean()
    }

    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return RetrfitFactory.instance.create(OrderApi::class.java).confirmOrder(ConfirmOrderReq(orderId)).convertBoolean()
    }
}