package com.yyzh.goodscenter.persenter

import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import com.yyzh.goodscenter.common.GoodsConstant
import com.yyzh.goodscenter.persenter.view.GoodsDetailView
import com.yyzh.goodscenter.service.bean.Goods
import com.yyzh.goodscenter.service.imp.GoodsServiceImp
import javax.inject.Inject

class GoodsDetailPersenter @Inject constructor():BasePersenter<GoodsDetailView>() {
    @Inject
    lateinit var goodsServiceImp: GoodsServiceImp

    fun getGoodsList(goodsId: Int){
        if(!checkNetWork()) return
        mView.showDialog()
        goodsServiceImp.getGoodsDetail(goodsId).execute(object :BaseSubscriber<Goods>(mView){
            override fun onNext(t: Goods) {
                super.onNext(t)
                mView.onGoodsDetailResult(t)
            }
        },lifecycleProvider)
    }
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                     goodsCount: Int, goodsSku: String){
        if(!checkNetWork()) return
        mView.showDialog()
        goodsServiceImp.addCart(goodsId,goodsDesc,goodsIcon,goodsPrice,goodsCount,goodsSku).execute(object :BaseSubscriber<Int>(mView){
            override fun onNext(t: Int) {
                super.onNext(t)
                AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t)
                mView.onAddCartResult(t)
            }
        },lifecycleProvider)
    }
}