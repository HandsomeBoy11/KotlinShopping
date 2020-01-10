package com.yyzh.paycenter.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.yyzh.paycenter.injection.module.PayModule
import com.yyzh.paycenter.ui.activity.CashRegisterActivity
import dagger.Component

/*
    支付Component
 */
@PerComponentScope
@Component(dependencies = [ActivityComponent::class],modules = [PayModule::class])
interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}
