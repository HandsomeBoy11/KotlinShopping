package com.yyzh.ordercenter.persenter.view

import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.presenter.view.BaseView

interface ConfirmOrderView :BaseView{
    //获取订单回调
    fun onGetOrderByIdResult(result: Order)
    //提交订单回调
    fun onSubmitOrderResult(result:Boolean)
}