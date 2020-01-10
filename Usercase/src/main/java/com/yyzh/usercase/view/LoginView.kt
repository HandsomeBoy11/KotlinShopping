package com.yyzh.usercase.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.service.bean.UserInfo

interface LoginView:BaseView {
    fun loginResult(result:UserInfo)
    fun errorResult(e: String)
}