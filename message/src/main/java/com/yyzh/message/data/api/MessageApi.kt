package com.yyzh.message.data.api

import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.message.service.bean.Message
import rx.Observable
import retrofit2.http.POST

/*
    消息 接口
 */
interface MessageApi {

    /*
        获取消息列表
     */
    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}
