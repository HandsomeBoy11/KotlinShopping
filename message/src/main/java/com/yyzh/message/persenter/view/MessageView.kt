package com.yyzh.message.persenter.view

import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.message.service.bean.Message

interface MessageView:BaseView{
    fun onGetMessageList(list:MutableList<Message>?)
}