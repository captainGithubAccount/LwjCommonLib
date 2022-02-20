package com.lwj.common.base

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitFactory() {
    private var retrofit: Retrofit? = null

    //获取retrofit客户端
    fun getRetrofit(baseUrl: String, interceptor: Interceptor? = null): Retrofit {
        return retrofit ?: createRetrofitClient(baseUrl, interceptor)
    }

    //配置retrofit
    private fun createRetrofitClient(baseUrl: String, interceptor: Interceptor? = null): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())//用于将@Body 注解里面传入的参数转变为json格式数据(里面参数可以为map也可以为bean对象, 甚至可以是满足json格式的字符串等一切可以转化为json格式数据的对象)
            .client(createOkhttpClient(interceptor))
            .build()
    }

    //配置okhttp
    private fun createOkhttpClient(interceptor: Interceptor?): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder() //配置http的log拦截器
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .apply {
                if(interceptor != null) {
                    this.addInterceptor(interceptor)
                }
            } //连接超时时间30 单位seconds(秒)
            .connectTimeout(30, TimeUnit.SECONDS) //读时间30 单位seconds(秒)
            .readTimeout(30, TimeUnit.SECONDS) //写时间30 单位seconds(秒)
            .writeTimeout(30, TimeUnit.SECONDS)

        return clientBuilder.build()
    }


}