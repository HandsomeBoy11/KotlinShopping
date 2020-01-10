package com.yyzh.goodscenter.data.api

import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.goodscenter.data.protocol.GetGoodsDetailReq
import com.yyzh.goodscenter.data.protocol.GetGoodsListByKeywordReq
import com.yyzh.goodscenter.data.protocol.GetGoodsListReq
import com.yyzh.goodscenter.service.bean.Goods
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品接口
 */
interface GoodsApi {
    /*
        获取商品列表
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GetGoodsListReq): Observable<BaseResp<MutableList<Goods>?>>


      /*获取商品列表*/

    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GetGoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>


        /*获取商品详情*/

    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GetGoodsDetailReq): Observable<BaseResp<Goods>>
}
