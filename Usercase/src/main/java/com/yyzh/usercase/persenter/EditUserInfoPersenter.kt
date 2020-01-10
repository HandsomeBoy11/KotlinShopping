package com.yyzh.usercase.persenter

import com.kotlin.base.rx.BaseException
import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.usercase.service.bean.UserInfo
import com.yyzh.usercase.service.imp.UserServiceImp
import com.yyzh.usercase.view.UserInfoView
import javax.inject.Inject


class EditUserInfoPersenter @Inject constructor():BasePersenter<UserInfoView>() {
    @Inject
    lateinit var userServiceImp: UserServiceImp

    fun editUserInfo(userIcon: String, userName: String, gender: String, sign: String) {
        if (!checkNetWork())
            return
        mView.showDialog()
        userServiceImp.editUserInfo(userIcon,userName,gender,sign)
            .execute(object :BaseSubscriber<UserInfo>(mView){
                override fun onNext(t: UserInfo) {
                    mView.onEditUserInfoResult(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    if(e is BaseException){
                        val msg = e.msg
                        mView.onError(msg)
                    }
                }
            },lifecycleProvider)
    }

}