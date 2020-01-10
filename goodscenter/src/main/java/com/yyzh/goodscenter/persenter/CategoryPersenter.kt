package com.yyzh.goodscenter.persenter

import android.widget.Toast
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.goodscenter.persenter.view.CategoryView
import com.yyzh.goodscenter.service.bean.Category
import com.yyzh.goodscenter.service.imp.CategoryServiceImp
import javax.inject.Inject

class CategoryPersenter @Inject constructor():BasePersenter<CategoryView>() {
    @Inject
    lateinit var categoryServiceImp: CategoryServiceImp

    fun getCategory(parentId:Int){
        if(!checkNetWork()) return
        mView.showDialog()
        categoryServiceImp.getCategory(parentId).execute(object :BaseSubscriber<MutableList<Category>?>(mView){
            override fun onNext(t: MutableList<Category>?) {
                super.onNext(t)
                mView.onGetCategoryResult(t)
            }
        },lifecycleProvider)
    }
}