package com.yyzh.goodscenter.service.imp

import com.kotlin.goods.data.protocol.AddCartReq
import com.kotlin.goods.data.protocol.DeleteCartReq
import com.kotlin.goods.data.protocol.SubmitCartReq
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.baselibrary.ext.convertBoolean
import com.yyzh.goodscenter.data.api.CartApi
import com.yyzh.goodscenter.service.CartService
import com.yyzh.goodscenter.service.bean.CartGoods
import rx.Observable
import javax.inject.Inject

/*
    商品 业务层 接口
 */
class CartServiceImp @Inject constructor() : CartService {

    /*
        获取购物车列表
     */
    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return RetrfitFactory.instance.create(CartApi::class.java).getCartList().convert()
    }

    /*添加商品到购物车*/
    override fun addCart(
        goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ): Observable<Int> {
        return RetrfitFactory.instance.create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)).convert()
    }

    /*删除购物车商品*/
    override fun deleteCartList(list: List<Int>): Observable<Boolean> {
        return RetrfitFactory.instance.create(CartApi::class.java).deleteCartList(DeleteCartReq(list)).convertBoolean()
    }

    /*提交购物车商品*/
    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> {
        return RetrfitFactory.instance.create(CartApi::class.java).submitCart(SubmitCartReq(list,totalPrice)).convert()
    }
}
