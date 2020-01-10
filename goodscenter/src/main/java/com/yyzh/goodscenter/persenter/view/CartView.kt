package com.yyzh.goodscenter.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.goodscenter.service.bean.CartGoods

interface CartView:BaseView {
    //获取购物车列表
    fun onGetCartListResult(result:MutableList<CartGoods>?)
    //删除购物车商品
    fun onDeleteCartResult(result:Boolean)
    //提交购物车商品
    fun onComitCartResult(result:Int)
}