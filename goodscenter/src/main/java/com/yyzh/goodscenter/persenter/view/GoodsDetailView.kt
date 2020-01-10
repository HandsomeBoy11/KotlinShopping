package com.yyzh.goodscenter.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.goodscenter.service.bean.Goods

interface GoodsDetailView:BaseView {
    fun onGoodsDetailResult(result: Goods)
    fun onAddCartResult(i:Int)
}