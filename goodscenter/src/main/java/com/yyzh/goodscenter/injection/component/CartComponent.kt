package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.CartModule
import com.yyzh.goodscenter.ui.fragment.CartFragment

import dagger.Component

/*
    购物车Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [CartModule::class])
interface CartComponent {
    fun inject(fragment: CartFragment)
}
