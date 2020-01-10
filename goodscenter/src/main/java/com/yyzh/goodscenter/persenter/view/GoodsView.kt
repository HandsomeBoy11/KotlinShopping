package com.yyzh.goodscenter.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.goodscenter.service.bean.Category
import com.yyzh.goodscenter.service.bean.Goods

interface GoodsView:BaseView {
    //获取商品分类列表
    fun onGetGoodsListResult(result: MutableList<Goods>?)
}