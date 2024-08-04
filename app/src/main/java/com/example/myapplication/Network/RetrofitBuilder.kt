package com.example.myapplication.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    fun create (): ApiRequestHandle = Retrofit.Builder()
        .baseUrl(BaseUrl.url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequestHandle::class.java)
}