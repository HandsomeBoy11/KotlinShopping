package com.yyzh.goodscenter.persenter

import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.goodscenter.persenter.view.CartView
import com.yyzh.goodscenter.service.bean.CartGoods
import com.yyzh.goodscenter.service.imp.CartServiceImp
import javax.inject.Inject

class CartPersenter @Inject constructor() : BasePersenter<CartView>() {
    @Inject
    lateinit var mCartServiceImp: CartServiceImp

    fun getCartList() {
        if (!checkNetWork()) return
        mView.showDialog()
        mCartServiceImp.getCartList().execute(object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
            override fun onNext(t: MutableList<CartGoods>?) {
                super.onNext(t)
                mView.onGetCartListResult(t)
            }
        }, lifecycleProvider)
    }


    fun deleteCartList(list: List<Int>) {
        if (!checkNetWork()) return
        mView.showDialog()
        mCartServiceImp.deleteCartList(list).execute(object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                super.onNext(t)
                mView.onDeleteCartResult(t)
            }
        }, lifecycleProvider)
    }

    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long) {
        if (!checkNetWork()) return
        mView.showDialog()
        mCartServiceImp.submitCart(list, totalPrice).execute(object : BaseSubscriber<Int>(mView) {
            override fun onNext(t: Int) {
                super.onNext(t)
                mView.onComitCartResult(t)
            }
        }, lifecycleProvider)
    }
}