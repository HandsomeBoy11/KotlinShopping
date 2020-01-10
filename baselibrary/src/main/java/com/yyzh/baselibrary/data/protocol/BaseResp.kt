package com.yyzh.baselibrary.data.protocol

data class BaseResp<T>(val status:Int, val message:String, val data:T?) {
}