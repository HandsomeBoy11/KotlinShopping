package com.yyzh.message.persenter

import com.yyzh.baselibrary.ext.execute
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.message.persenter.view.MessageView
import com.yyzh.message.service.bean.Message
import com.yyzh.message.service.imp.MessageServiceImp
import javax.inject.Inject

class MessagePersenter @Inject constructor():BasePersenter<MessageView>() {
    @Inject
    lateinit var mMessageServiceImp: MessageServiceImp
    fun getMessageList(){
        if(!checkNetWork())return
        mView.showDialog()
        mMessageServiceImp.getMessageList().execute(object :BaseSubscriber<MutableList<Message>?>(mView){
            override fun onNext(t: MutableList<Message>?) {
                super.onNext(t)
                mView.onGetMessageList(t)
            }
        },lifecycleProvider)
    }
}