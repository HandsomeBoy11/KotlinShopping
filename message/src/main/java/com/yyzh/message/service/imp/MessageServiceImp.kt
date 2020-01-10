package com.yyzh.message.service.imp

import com.yyzh.baselibrary.data.net.RetrfitFactory
import com.yyzh.baselibrary.ext.convert
import com.yyzh.message.data.api.MessageApi
import com.yyzh.message.service.MessageService
import com.yyzh.message.service.bean.Message
import rx.Observable
import javax.inject.Inject

class MessageServiceImp @Inject constructor():MessageService {
    override fun getMessageList(): Observable<MutableList<Message>?> {
       return  RetrfitFactory.instance.create(MessageApi::class.java).getMessageList().convert()
    }
}