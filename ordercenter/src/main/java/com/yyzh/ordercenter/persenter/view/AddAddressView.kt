package com.yyzh.ordercenter.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView

interface AddAddressView:BaseView {

    fun onAddAddressResult(str:String)
    fun onEditShipAddressResult(str:Boolean)
}