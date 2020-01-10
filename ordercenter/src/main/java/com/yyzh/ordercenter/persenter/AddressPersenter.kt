package com.yyzh.ordercenter.persenter

import com.kotlin.order.data.protocol.ShipAddress
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.baselibrary.rx.DataNotNull
import com.yyzh.baselibrary.utlis.NetWorkUtils
import com.yyzh.ordercenter.persenter.view.AddressView
import com.yyzh.ordercenter.service.imp.AddressServiceImp
import javax.inject.Inject

class AddressPersenter @Inject constructor() : BasePersenter<AddressView>() {
    @Inject
    lateinit var addressServiceImp: AddressServiceImp

    /**
     * 获取地址
     */
    fun getAddressList() {
        if(!checkNetWork()) return
        mView.showDialog()
        addressServiceImp.getAddressList().execute(object : BaseSubscriber<BaseResp<MutableList<ShipAddress>?>>(mView) {
            override fun onNext(t: BaseResp<MutableList<ShipAddress>?>) {
                super.onNext(t)
                    mView.onGetAddressResult(t.data)
            }
            override fun onError(e: Throwable) {
                super.onError(e)
                mView.onError(e.message.toString())
            }
        }, lifecycleProvider)
    }
    fun deleteAddressList(id:Int) {
        if(!checkNetWork()) return
        mView.showDialog()
        addressServiceImp.deleteAddressList(id).execute(object : BaseSubscriber<BaseResp<String>>(mView) {
            override fun onNext(t: BaseResp<String>) {
                super.onNext(t)
                mView.onDeleteAddressResult(t.data?:"")
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.onError(e.message.toString())
            }
        }, lifecycleProvider)
    }
    fun editShipAddress(address: ShipAddress) {
        if(!checkNetWork()) return
        mView.showDialog()
        addressServiceImp.editShipAddress(address).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onEditddressResult(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                mView.onError(e.message.toString())
            }
        }, lifecycleProvider)
    }
}