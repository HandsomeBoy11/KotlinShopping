package com.yyzh.ordercenter.persenter.view

import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.presenter.view.BaseView

/**
 * 订单回调
 */
interface OrderView:BaseView {
    //获取订单列表回调
    fun onGetOrderListResult(result:MutableList<Order>?)
    //确认订单回调
    fun onConfirmOrderResult(result:Boolean)
    //取消订单回调
    fun onCancelOrderResult(result:Boolean)
}