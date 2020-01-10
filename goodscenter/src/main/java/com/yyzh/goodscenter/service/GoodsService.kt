package com.yyzh.goodscenter.service

import com.yyzh.goodscenter.service.bean.Category
import com.yyzh.goodscenter.service.bean.Goods
import rx.Observable

/*
    商品 业务层 接口
 */
interface GoodsService {

    /*
        获取分类
     */
    fun getGoods(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>

    /*获取商品详情*/
    fun getGoodsDetail(goodsId: Int): Observable<Goods>

    /*通过关键字获取商品*/
    fun getGoodsByKeyWord(keyWord: String, pageNo: Int): Observable<MutableList<Goods>?>

    /*添加购物车*/
    fun addCart(
        goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
        goodsCount: Int, goodsSku: String
    ): Observable<Int>
}
