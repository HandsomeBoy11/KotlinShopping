package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.CategoryModule
import com.yyzh.goodscenter.ui.activity.GoodsActivity
import com.yyzh.goodscenter.ui.fragment.CategoryFragment

import dagger.Component

/*
    分类模块Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [CategoryModule::class])
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}
