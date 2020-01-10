package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.GoodsDetailModule
import com.yyzh.goodscenter.ui.fragment.GoodsDetailTabOneFragment

import dagger.Component

/*
    商品详情模块Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [GoodsDetailModule::class])
interface GoodsDetailComponent {
    fun inject(fragment: GoodsDetailTabOneFragment)
}
