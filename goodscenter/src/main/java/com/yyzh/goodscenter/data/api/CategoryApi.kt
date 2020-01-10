package com.yyzh.goodscenter.data.api

import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.goodscenter.data.protocol.GetCategoryReq
import com.yyzh.goodscenter.service.bean.Category
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

/*
    商品分类接口
 */
interface CategoryApi {
    /*
        获取商品分类列表
     */
    @POST("category/getCategory")
    fun getCategory(@Body req: GetCategoryReq): Observable<BaseResp<MutableList<Category>?>>
}
