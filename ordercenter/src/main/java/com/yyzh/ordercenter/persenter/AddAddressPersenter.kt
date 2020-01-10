package com.yyzh.ordercenter.persenter

import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.baselibrary.rx.DataNotNull
import com.yyzh.ordercenter.persenter.view.AddAddressView
import com.yyzh.ordercenter.persenter.view.AddressView
import com.yyzh.ordercenter.service.imp.AddressServiceImp
import javax.inject.Inject

class AddAddressPersenter @Inject constructor() : BasePersenter<AddAddressView>() {
    @Inject
    lateinit var addressServiceImp: AddressServiceImp

    /**
     * 获取地址
     */
    fun addShipAddress(shipUserName: String,  shipUserMobile: String,  shipAddress: String) {
        if(!checkNetWork()) return
        mView.showDialog()
        addressServiceImp.addShipAddress(shipUserName,  shipUserMobile,  shipAddress).execute(object : BaseSubscriber<BaseResp<String>>(mView) {
            override fun onNext(t: BaseResp<String>) {
                super.onNext(t)
                mView.onAddAddressResult(t.data?:"")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.onError(e.message.toString())
            }
        }, lifecycleProvider)
    }
    /*
      修改收货人信息
   */
    fun editShipAddress(address:ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showDialog()
        addressServiceImp.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onEditShipAddressResult(t)
            }
        }, lifecycleProvider)

    }

}