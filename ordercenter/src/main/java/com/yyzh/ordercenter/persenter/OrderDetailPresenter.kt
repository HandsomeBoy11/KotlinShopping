package com.yyzh.ordercenter.persenter

import com.kotlin.order.data.protocol.Order
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.ordercenter.persenter.view.OrderDetailView
import com.yyzh.ordercenter.service.OrderService

import javax.inject.Inject

/*
    订单详情页Presenter
 */
class OrderDetailPresenter @Inject constructor() : BasePersenter<OrderDetailView>() {

    @Inject
    lateinit var orderService: OrderService

    /*
        根据ID查询订单
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showDialog()
        orderService.getOrderById(orderId).execute(object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                    mView.onGetOrderByIdResult(t)
            }
        }, lifecycleProvider)

    }

}
