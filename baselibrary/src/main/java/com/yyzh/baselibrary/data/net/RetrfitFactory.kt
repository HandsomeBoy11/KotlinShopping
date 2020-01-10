package com.yyzh.baselibrary.data.net

import com.yyzh.baselibrary.common.BaseConstant
import com.yyzh.baselibrary.utlis.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrfitFactory private constructor() {

    companion object {
        val instance: RetrfitFactory  by lazy { RetrfitFactory() }
    }

    private var retrfit: Retrofit
    private var interceptor:Interceptor

    init {
        interceptor= Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("Charset","utf-8")
                .addHeader("token", AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                .build()
            chain.proceed(request)
        }

        retrfit = Retrofit.Builder()
            .baseUrl(BaseConstant.SERVICEADDRESS)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(initClient())
            .build()
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(initLogInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

    }

    private fun initLogInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun <T> create(service: Class<T>): T {
        return retrfit.create(service)
    }
}