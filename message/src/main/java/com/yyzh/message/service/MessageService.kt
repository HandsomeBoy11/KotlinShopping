package com.yyzh.message.service

import com.yyzh.message.service.bean.Message
import rx.Observable

interface MessageService {
    fun getMessageList():Observable<MutableList<Message>?>
}