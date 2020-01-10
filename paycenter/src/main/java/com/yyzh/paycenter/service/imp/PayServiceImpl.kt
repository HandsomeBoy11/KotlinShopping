package com.yyzh.paycenter.service.imp


import com.kotlin.pay.data.protocol.GetPaySignReq
import com.kotlin.pay.data.protocol.PayOrderReq
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.baselibrary.ext.convertBoolean
import com.yyzh.paycenter.data.api.PayApi
import com.yyzh.paycenter.service.PayService
import rx.Observable
import javax.inject.Inject

/*
    支付 业务实现类
 */
class PayServiceImpl @Inject constructor(): PayService {

    /*
        获取支付签名
     */
    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return RetrfitFactory.instance.create(PayApi::class.java).getPaySign(GetPaySignReq(orderId,totalPrice)).convert()
    }

    /*
        支付订单，同步订单状态
     */
    override fun payOrder(orderId: Int): Observable<Boolean> {
        return RetrfitFactory.instance.create(PayApi::class.java).payOrder(PayOrderReq(orderId)).convertBoolean()
    }
}
