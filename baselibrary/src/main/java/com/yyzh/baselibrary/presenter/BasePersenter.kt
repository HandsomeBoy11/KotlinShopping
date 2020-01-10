package com.yyzh.baselibrary.presenter

import android.content.Context
import com.trello.rxlifecycle.LifecycleProvider
import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.baselibrary.utlis.NetWorkUtils
import javax.inject.Inject

open class BasePersenter<T:BaseView> {
    lateinit var mView:T

    //Dagger注入，Rx生命周期管理
    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*>


    @Inject
    lateinit var context: Context

    /*
        检查网络是否可用
     */
    fun checkNetWork():Boolean{
        if(NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.onError("网络不可用")
        return false
    }
}