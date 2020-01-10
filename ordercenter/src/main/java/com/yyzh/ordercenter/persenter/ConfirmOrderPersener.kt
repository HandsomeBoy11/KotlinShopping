package com.yyzh.ordercenter.persenter

import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.ordercenter.persenter.view.ConfirmOrderView
import com.yyzh.ordercenter.service.OrderService
import javax.inject.Inject

class ConfirmOrderPersener @Inject constructor() :BasePersenter<ConfirmOrderView>() {
    @Inject
    lateinit var serviceImp:OrderService
    fun getOrderById(orderId: Int){
        if(!checkNetWork())return
        mView.showDialog()
        serviceImp.getOrderById(orderId).execute(object :BaseSubscriber<Order>(mView){
            override fun onNext(t: Order) {
                super.onNext(t)
                mView.onGetOrderByIdResult(t)
            }
        },lifecycleProvider)
    }
    fun submitOrder(order: Order){
        if(!checkNetWork())return
        mView.showDialog()
        serviceImp.submitOrder(order).execute(object :BaseSubscriber<Boolean>(mView){
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onSubmitOrderResult(t)
            }
        },lifecycleProvider)
    }
}