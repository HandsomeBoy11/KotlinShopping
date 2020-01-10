package com.yyzh.ordercenter.service

import com.kotlin.order.data.protocol.Order
import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.rx.DataNotNull
import rx.Observable

interface OrderService {
    /*
     根据ID查询订单
  */
    fun getOrderById(orderId: Int): Observable<Order>

    /*
    提交订单
 */
    fun submitOrder(order: Order): Observable<Boolean>

    /*
    根据状态查询订单列表
 */
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>

    /*
    取消订单
 */
    fun cancelOrder(orderId: Int): Observable<Boolean>

    /*
        确认订单
     */
    fun confirmOrder(orderId: Int): Observable<Boolean>

}