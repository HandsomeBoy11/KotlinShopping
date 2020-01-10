package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.AddressModule
import com.kotlin.user.injection.module.OrderModule
import com.yyzh.ordercenter.ui.activity.OrderAddressActivity
import com.yyzh.ordercenter.ui.activity.OrderConfirmActivity
import com.yyzh.ordercenter.ui.activity.OrderDetailActivity
import com.yyzh.ordercenter.ui.activity.ShipAddressEditActivity
import com.yyzh.ordercenter.ui.fragment.OrderFragment

import dagger.Component

/*
    订单Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [OrderModule::class])
interface OrderComponent {
    fun inject(activity:OrderConfirmActivity)
    fun inject(activity: OrderDetailActivity)
    fun inject(fragment: OrderFragment)
}
