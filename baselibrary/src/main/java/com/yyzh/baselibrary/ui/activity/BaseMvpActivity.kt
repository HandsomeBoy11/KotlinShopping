package com.yyzh.baselibrary.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.baselibrary.weights.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

open abstract class BaseMvpActivity<T:BasePersenter<*>>:BaseActivity(),BaseView {
    private lateinit var mLoadingDialog: ProgressLoading
    @Inject
    lateinit var mPersenter:T

    lateinit var mActivityComponent:ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityInjection()
        injectComponent()
        ARouter.getInstance().inject(this)
        //初始加载框
        mLoadingDialog = ProgressLoading.create(this)
    }
    override fun showDialog() {
        mLoadingDialog.showLoading()
    }

    override fun hideDialog() {
        mLoadingDialog.hideLoading()
    }

    override fun onError(text:String) {
        toast(text)
    }
    /*
     Dagger注册
  */
    protected abstract fun injectComponent()
    /*
       初始Activity Component
    */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()

    }

}