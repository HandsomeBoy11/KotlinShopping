package com.yyzh.ordercenter.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.user.injection.component.DaggerAddressComponent
import com.kotlin.user.injection.module.AddressModule
import com.yyzh.baselibrary.ext.enable
import com.yyzh.baselibrary.ext.onClick
import com.yyzh.baselibrary.ui.activity.BaseMvpActivity
import com.yyzh.ordercenter.R
import com.yyzh.ordercenter.common.OrderConstant
import com.yyzh.ordercenter.persenter.AddAddressPersenter
import com.yyzh.ordercenter.persenter.view.AddAddressView
import kotlinx.android.synthetic.main.activity_address_edit.*
import org.jetbrains.anko.toast

class ShipAddressEditActivity : BaseMvpActivity<AddAddressPersenter>(), AddAddressView, View.OnClickListener {


    private  var address: ShipAddress?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_edit)
        initView()
        initData()
    }

    private fun initData() {
        address=intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        address?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }
    }

    private fun initView() {
        mSaveBtn.onClick(this)
        mSaveBtn.enable(mShipNameEt) { isEnable() }
        mSaveBtn.enable(mShipMobileEt) { isEnable() }
        mSaveBtn.enable(mShipAddressEt) { isEnable() }
    }

    private fun isEnable(): Boolean {
        return mShipNameEt.text.isNullOrEmpty().not()
                && mShipMobileEt.text.isNullOrEmpty().not()
                && mShipAddressEt.text.isNullOrEmpty().not()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.mSaveBtn -> {
                if(address==null){
                    mPersenter.addShipAddress(mShipNameEt.text.toString().trim(),
                        mShipMobileEt.text.toString().trim(),
                        mShipAddressEt.text.toString().trim())
                }else{
                    address?.apply {
                        shipUserName=mShipNameEt.text.toString().trim()
                        shipUserMobile=mShipMobileEt.text.toString().trim()
                        shipAddress=mShipAddressEt.text.toString().trim()
                        mPersenter.editShipAddress(this)
                    }

                }

            }
        }
    }

    override fun injectComponent() {
        DaggerAddressComponent.builder().activityComponent(mActivityComponent).addressModule(AddressModule()).build()
            .inject(this)
        mPersenter.mView = this
    }

    override fun onAddAddressResult(str: String) {
        toast("保存成功")
        finish()
    }
    override fun onEditShipAddressResult(b: Boolean) {
            toast("修改成功")
            finish()
    }
}