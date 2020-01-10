package com.kotlin.user.injection.module

import com.yyzh.goodscenter.service.CategoryService
import com.yyzh.goodscenter.service.GoodsService
import com.yyzh.goodscenter.service.imp.CategoryServiceImp
import com.yyzh.goodscenter.service.imp.GoodsServiceImp
import dagger.Module
import dagger.Provides

/*
    商品模块Module
 */
@Module
class GoodsModule {

    @Provides
    fun provideGoodsService(goodsServiceImp: GoodsServiceImp): GoodsService {
        return goodsServiceImp
    }

}

