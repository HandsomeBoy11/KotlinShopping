package com.yyzh.baselibrary.presenter.view

open interface BaseView {
    fun showDialog()
    fun hideDialog()
    fun onError(text:String)
}