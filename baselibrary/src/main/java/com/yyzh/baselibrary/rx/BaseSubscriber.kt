package com.yyzh.baselibrary.rx

import com.kotlin.base.rx.BaseException
import com.yyzh.baselibrary.presenter.view.BaseView
import rx.Subscriber

open class BaseSubscriber<T>(val mBaseView: BaseView): Subscriber<T>() {
    override fun onNext(t: T) {

    }

    override fun onCompleted() {
        mBaseView.hideDialog()
    }

    override fun onError(e: Throwable) {
        mBaseView.hideDialog()
        if (e is BaseException){
            mBaseView.onError(e.msg)
        }
    }
}