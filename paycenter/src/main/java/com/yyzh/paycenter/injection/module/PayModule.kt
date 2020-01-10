package com.yyzh.paycenter.injection.module

import com.yyzh.paycenter.service.PayService
import com.yyzh.paycenter.service.imp.PayServiceImpl
import dagger.Module
import dagger.Provides

/*
    支付 Module
 */
@Module
class PayModule {

    @Provides
    fun providePayService(payService: PayServiceImpl): PayService {
        return payService
    }

}
