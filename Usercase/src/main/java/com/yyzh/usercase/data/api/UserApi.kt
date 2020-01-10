package com.yyzh.usercase.data.api

import com.kotlin.user.data.protocol.RegisterReq
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.launchlearn.main.model.bean.ArticleList
import com.yyzh.usercase.data.protocol.EditUserReq
import com.yyzh.usercase.data.protocol.LoginReq
import com.yyzh.usercase.service.bean.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rx.Observable

interface UserApi {
    /**
     * 用户注册
     */
    @POST("/userCenter/register")
    fun register(@Body registReq: RegisterReq): Observable<BaseResp<Boolean>>

    /**
     * 用户登录
     */
    @POST("userCenter/login")
    fun login(@Body req: LoginReq): Observable<BaseResp<UserInfo>>

    /*
        编辑用户资料
     */
    @POST("userCenter/editUser")
    fun editUser(@Body req: EditUserReq):Observable<BaseResp<UserInfo>>
}