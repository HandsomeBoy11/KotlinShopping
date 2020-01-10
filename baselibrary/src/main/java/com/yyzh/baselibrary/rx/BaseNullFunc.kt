package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.yyzh.baselibrary.data.protocol.BaseResp
import com.yyzh.baselibrary.rx.DataNotNull
import rx.Observable
import rx.functions.Func1

/*
    通用数据类型转换封装
 */
class BaseNullFunc<T>:Func1<BaseResp<T>,Observable<BaseResp<T>>>{
    override fun call(t: BaseResp<T>): Observable<BaseResp<T>> {
        if (t.status != ResultCode.SUCCESS){
            return Observable.error(BaseException(t.status,t.message))
        }
        return Observable.just(t)
    }

}
