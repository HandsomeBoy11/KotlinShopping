package com.yyzh.goodscenter.persenter

import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.goodscenter.persenter.view.GoodsView
import com.yyzh.goodscenter.service.bean.Goods
import com.yyzh.goodscenter.service.imp.GoodsServiceImp
import javax.inject.Inject

class GoodsPersenter @Inject constructor():BasePersenter<GoodsView>() {
    @Inject
    lateinit var goodsServiceImp: GoodsServiceImp

    fun getGoodsList(categoryId: Int, pageNo: Int){
        if(!checkNetWork()) return
        mView.showDialog()
        goodsServiceImp.getGoods(categoryId,pageNo).execute(object :BaseSubscriber<MutableList<Goods>?>(mView){
            override fun onNext(t: MutableList<Goods>?) {
                super.onNext(t)
                mView.onGetGoodsListResult(t)
            }
        },lifecycleProvider)
    }
    fun getGoodsByKeyWord(keyWord: String, pageNo: Int){
        if(!checkNetWork()) return
        mView.showDialog()
        goodsServiceImp.getGoodsByKeyWord(keyWord,pageNo).execute(object :BaseSubscriber<MutableList<Goods>?>(mView){
            override fun onNext(t: MutableList<Goods>?) {
                super.onNext(t)
                mView.onGetGoodsListResult(t)
            }
        },lifecycleProvider)
    }
}