package com.yyzh.usercase.persenter

import com.kotlin.base.common.ResultCode
import com.kotlin.base.rx.BaseException
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.service.bean.UserInfo
import com.yyzh.usercase.service.imp.UserServiceImp
import com.yyzh.usercase.view.LoginView
import com.yyzh.usercase.view.RegisterView
import javax.inject.Inject


class LoginPersenter @Inject constructor():BasePersenter<LoginView>() {
    @Inject
    lateinit var loginServiceImp: UserServiceImp

    fun login(mobile: String, pwd: String,pushId:String){
        if (!checkNetWork())
            return
        mView.showDialog()
        loginServiceImp.login(mobile,pwd,pushId)
            .execute(object :BaseSubscriber<UserInfo>(mView){
                override fun onNext(t: UserInfo) {
                    mView.loginResult(t)
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