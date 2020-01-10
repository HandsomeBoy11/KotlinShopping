package com.yyzh.usercase.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.launchlearn.main.model.bean.ArticleList

interface RegisterView:BaseView {
    fun registResult(result:Boolean)
    fun errorResult(e: String)
}