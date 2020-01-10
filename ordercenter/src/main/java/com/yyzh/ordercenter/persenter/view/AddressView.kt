package com.yyzh.ordercenter.persenter.view

import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.presenter.view.BaseView

interface AddressView :BaseView{
    fun onGetAddressResult(result:MutableList<ShipAddress>?)
    fun onDeleteAddressResult(result:String)
    fun onEditddressResult(result:Boolean)
}