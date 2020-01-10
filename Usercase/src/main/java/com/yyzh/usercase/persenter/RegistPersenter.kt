package com.yyzh.usercase.persenter

import com.kotlin.base.common.ResultCode
import com.kotlin.base.rx.BaseException
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.service.imp.UserServiceImp
import com.yyzh.usercase.view.RegisterView
import javax.inject.Inject


class RegistPersenter @Inject constructor():BasePersenter<RegisterView>() {
    @Inject
    lateinit var registService:UserServiceImp

    fun regist(phone:String,verifyCode:String,pwd:String){
        mView.showDialog()
        registService.register(phone,verifyCode,pwd)
            .execute(object :BaseSubscriber<Boolean>(mView){
                override fun onNext(t: Boolean) {
                    mView.registResult(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if(e is BaseException){
                        val msg = e.msg
                        mView.errorResult(msg)
                    }
                }
            },lifecycleProvider)

    }

}