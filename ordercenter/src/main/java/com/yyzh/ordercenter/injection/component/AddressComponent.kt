package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.AddressModule
import com.yyzh.ordercenter.ui.activity.OrderAddressActivity
import com.yyzh.ordercenter.ui.activity.ShipAddressEditActivity

import dagger.Component

/*
    收货地址Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(AddressModule::class))
interface AddressComponent {
    fun inject(activity:OrderAddressActivity)
    fun inject(activity:ShipAddressEditActivity)

}
