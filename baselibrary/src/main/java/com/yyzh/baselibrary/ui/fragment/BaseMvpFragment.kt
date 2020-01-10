package com.yyzh.baselibrary.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.yyzh.baselibrary.presenter.BasePersenter
import com.yyzh.baselibrary.presenter.view.BaseView
import com.yyzh.baselibrary.weights.ProgressLoading
import javax.inject.Inject

 abstract class BaseMvpFragment<T: BasePersenter<*>>:BaseFragment() ,BaseView{
    @Inject
    lateinit var mPresenter: T
    private lateinit var mLoading: ProgressLoading

    lateinit var mActivityComponent: ActivityComponent
    override fun showDialog() {
        mLoading.showLoading()
    }

    override fun hideDialog() {
        mLoading.hideLoading()
    }

    override fun onError(text:String) {

    }

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

     }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         initActivityInjection()
         injectComponent()
         //初始加载框
         mLoading = ProgressLoading.create(activity!!)
         return super.onCreateView(inflater, container, savedInstanceState)
     }

    /*
        Dagger注册
     */
    protected abstract fun injectComponent()
    /*
    初始化Activity级别Component
 */
    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder().appComponent((activity!!.application as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity!!))
            .lifecycleProviderModule(LifecycleProviderModule(this))
            .build()

    }
}