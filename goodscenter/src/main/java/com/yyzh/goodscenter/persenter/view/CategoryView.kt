package com.yyzh.goodscenter.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.goodscenter.service.bean.Category

interface CategoryView:BaseView {
    //获取商品分类列表
    fun onGetCategoryResult(result: MutableList<Category>?)
}