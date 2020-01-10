package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.CategoryModule
import com.kotlin.user.injection.module.GoodsModule
import com.yyzh.goodscenter.ui.activity.GoodsActivity
import com.yyzh.goodscenter.ui.fragment.CategoryFragment

import dagger.Component

/*
    商品模块Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [GoodsModule::class])
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
}
