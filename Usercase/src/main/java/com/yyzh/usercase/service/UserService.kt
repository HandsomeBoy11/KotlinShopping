package com.yyzh.usercase.service

import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.service.bean.UserInfo
import rx.Observable

interface UserService {
    //用户注册
    fun register(mobile:String,pwd:String,verifyCode:String): Observable<Boolean>
    //登录
    fun login(mobile:String,pwd:String,pushId:String): Observable<UserInfo>
    //登录
    fun editUserInfo( userIcon: String,  userName: String,  gender: String,  sign: String): Observable<UserInfo>
}