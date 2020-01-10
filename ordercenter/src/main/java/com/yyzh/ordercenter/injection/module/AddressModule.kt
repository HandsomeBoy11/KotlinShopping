package com.kotlin.user.injection.module

import com.yyzh.ordercenter.service.AddressService
import com.yyzh.ordercenter.service.imp.AddressServiceImp
import dagger.Module
import dagger.Provides

/*
    用户模块Module
 */
@Module
class AddressModule {

    @Provides
    fun provideAddressService(serviceImp: AddressServiceImp): AddressService {
        return serviceImp
    }

}

