package com.yyzh.baselibrary.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseNullFunc
import com.trello.rxlifecycle.LifecycleProvider
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.rx.BaseSubscriber
import com.yyzh.baselibrary.rx.DataNotNull
import com.yyzh.baselibrary.utlis.GlideUtils
import com.yyzh.baselibrary.weights.DefaultTextWatcher
import rx.Observable

fun <T>Observable<T>.execute(sub:BaseSubscriber<T>,lifecycleProvider: LifecycleProvider<*>){
    this.subscribeOn(rx.schedulers.Schedulers.io())
        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribe(sub)

}

/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert():Observable<T>{
    return this.flatMap(BaseFunc())
}

/**
 * 返回data不为null
 */
fun <T> Observable<BaseResp<T>>.convertNull():Observable<BaseResp<T>>{
    return this.flatMap(BaseNullFunc())
}

/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean():Observable<Boolean>{
    return this.flatMap(BaseFuncBoolean())
}

/**
 *扩展的点击事件
 */
fun View.onClick(listener:View.OnClickListener):View{
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method:() -> Unit):View{
    setOnClickListener { method() }
    return this
}

/**
 *
 */
fun Button.enable(edit:EditText,mothed:()->Boolean){
    var btn=this
    edit.addTextChangedListener(object : DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)
            btn.isEnabled=mothed()
        }
    })
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

fun View.setVisable(boolean:Boolean){
    this.visibility=if(boolean) View.VISIBLE else View.GONE
}
