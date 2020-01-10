package com.yyzh.ordercenter.persenter

import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.ordercenter.persenter.view.OrderView
import com.yyzh.ordercenter.service.OrderService
import javax.inject.Inject

class OrderPersener @Inject constructor() :BasePersenter<OrderView>() {
    @Inject
    lateinit var orderService:OrderService
    /*
       根据订单状态获取订单列表
    */
    fun getOrderList(orderStatus: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showDialog()
        orderService.getOrderList(orderStatus).execute(object : BaseSubscriber<MutableList<Order>?>(mView) {
            override fun onNext(t: MutableList<Order>?) {
                mView.onGetOrderListResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        订单确认收货
     */
    fun confirmOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showDialog()
        orderService.confirmOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onConfirmOrderResult(t)
            }
        }, lifecycleProvider)

    }

    /*
        取消订单
     */
    fun cancelOrder(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showDialog()
        orderService.cancelOrder(orderId).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onCancelOrderResult(t)
            }
        }, lifecycleProvider)

    }
}