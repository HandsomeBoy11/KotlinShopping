package com.kotlin.user.injection.module

import com.yyzh.ordercenter.service.OrderService
import com.yyzh.ordercenter.service.imp.OrderServiceImp
import dagger.Module
import dagger.Provides

/*
    订单Module
 */
@Module
class OrderModule {

    @Provides
    fun provideOrderService(serviceImp: OrderServiceImp): OrderService {
        return serviceImp
    }

}

