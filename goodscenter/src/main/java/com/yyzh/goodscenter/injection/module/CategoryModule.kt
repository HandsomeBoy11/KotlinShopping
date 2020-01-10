package com.kotlin.user.injection.module

import com.yyzh.goodscenter.service.CategoryService
import com.yyzh.goodscenter.service.imp.CategoryServiceImp
import dagger.Module
import dagger.Provides

/*
    分类模块Module
 */
@Module
class CategoryModule {

    @Provides
    fun provideCategoryService(categoryServiceImp: CategoryServiceImp): CategoryService {
        return categoryServiceImp
    }

}

