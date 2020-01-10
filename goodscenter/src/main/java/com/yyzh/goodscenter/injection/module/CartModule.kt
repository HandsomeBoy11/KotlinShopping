package com.kotlin.user.injection.module

import com.yyzh.goodscenter.service.CartService
import com.yyzh.goodscenter.service.imp.CartServiceImp

import dagger.Module
import dagger.Provides

/*
    购物车Module
 */
@Module
class CartModule {
    @Provides
    fun provideCartService(cartServiceImp: CartServiceImp): CartService {
        return cartServiceImp
    }

}

