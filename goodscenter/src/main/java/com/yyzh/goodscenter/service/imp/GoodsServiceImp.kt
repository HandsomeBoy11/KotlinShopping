package com.yyzh.goodscenter.service.imp

import com.kotlin.goods.data.protocol.AddCartReq
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.goodscenter.data.api.CartApi
import com.yyzh.goodscenter.data.api.GoodsApi
import com.yyzh.goodscenter.data.protocol.GetGoodsDetailReq
import com.yyzh.goodscenter.data.protocol.GetGoodsListByKeywordReq
import com.yyzh.goodscenter.data.protocol.GetGoodsListReq
import com.yyzh.goodscenter.service.GoodsService
import com.yyzh.goodscenter.service.bean.Goods
import rx.Observable
import javax.inject.Inject

class GoodsServiceImp @Inject constructor() : GoodsService {
    override fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int> {
        return RetrfitFactory.instance.create(CartApi::class.java)
            .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)).convert()
    }

    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return RetrfitFactory.instance.create(GoodsApi::class.java).getGoodsDetail(GetGoodsDetailReq(goodsId)).convert()
    }

    override fun getGoods(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return RetrfitFactory.instance.create(GoodsApi::class.java).getGoodsList(GetGoodsListReq(categoryId, pageNo))
            .convert()
    }

    override fun getGoodsByKeyWord(keyWord: String, pageNo: Int): Observable<MutableList<Goods>?> {
        return RetrfitFactory.instance.create(GoodsApi::class.java)
            .getGoodsListByKeyword(GetGoodsListByKeywordReq(keyWord, pageNo)).convert()
    }
}