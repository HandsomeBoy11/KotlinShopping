package com.kotlin.user.injection.module

import com.yyzh.usercase.service.UserService
import com.yyzh.usercase.service.imp.UserServiceImp
import dagger.Module
import dagger.Provides

/*
    用户模块Module
 */
@Module
class UserModule {

    @Provides
    fun provideUserService(userService: UserServiceImp): UserService {
        return userService
    }

}

