package com.yyzh.usercase.service.imp

import com.kotlin.base.common.ResultCode
import com.kotlin.user.data.protocol.RegisterReq
import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.ext.convert
import com.yyzh.baselibrary.ext.convertBoolean
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.data.api.UserApi
import com.yyzh.usercase.data.protocol.EditUserReq
import com.yyzh.usercase.data.protocol.LoginReq
import com.yyzh.usercase.service.UserService
import com.yyzh.usercase.service.bean.UserInfo
import rx.Observable
import javax.inject.Inject

class UserServiceImp @Inject constructor() : UserService {


    override fun login(mobile: String, pwd: String,pushId:String): Observable<UserInfo> {
        return RetrfitFactory.instance.create(UserApi::class.java).login(LoginReq(mobile, pwd,pushId))
            .convert()
    }

    override fun register(mobile: String, pwd: String, verifyCode: String): Observable<Boolean> {
        return RetrfitFactory.instance.create(UserApi::class.java).register(RegisterReq(mobile, pwd, verifyCode))
            .convertBoolean()
    }
    override fun editUserInfo(userIcon: String, userName: String, gender: String, sign: String): Observable<UserInfo> {
        return  RetrfitFactory.instance.create(UserApi::class.java).editUser(EditUserReq(userIcon,userName,gender,sign))
            .convert()
    }
}