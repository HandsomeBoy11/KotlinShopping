package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.user.injection.module.UserModule
import com.yyzh.usercase.ui.activity.LoginActivity
import com.yyzh.usercase.ui.activity.RegistActivity
import com.yyzh.usercase.ui.activity.UserInfoActivity
import dagger.Component

/*
    用户模块Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity:RegistActivity)
    fun inject(activity: LoginActivity)
//    fun inject(activity:ForgetPwdActivity)
//    fun inject(activity:ResetPwdActivity)
    fun inject(activity: UserInfoActivity)
}
