package com.yyzh.goodscenter.service.imp

import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.goodscenter.data.api.CategoryApi
import com.yyzh.goodscenter.data.protocol.GetCategoryReq
import com.yyzh.goodscenter.service.CategoryService
import com.yyzh.goodscenter.service.bean.Category
import rx.Observable
import javax.inject.Inject

class CategoryServiceImp @Inject constructor():CategoryService {
    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return RetrfitFactory.instance.create(CategoryApi::class.java).getCategory(GetCategoryReq(parentId)).convert()
    }
}