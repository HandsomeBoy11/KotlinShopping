package com.kotlin.user.injection.module

import com.yyzh.message.service.MessageService
import com.yyzh.message.service.imp.MessageServiceImp
import dagger.Module
import dagger.Provides

/*
    消息模块Module
 */
@Module
class MessageModule {

    @Provides
    fun provideMessageService(serviceImp: MessageServiceImp): MessageService {
        return serviceImp
    }

}

