package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.MessageModule
import com.yyzh.baselibrary.ui.fragment.BaseMvpFragment
import com.yyzh.message.ui.fragment.MessageFragment

import dagger.Component

/*
    消息Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(MessageModule::class))
interface MessageComponent {
    fun inject(fragment:MessageFragment)


}
