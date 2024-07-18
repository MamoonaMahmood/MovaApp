package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

     val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val api : ApiRequestHandle by lazy {
        instance.create(ApiRequestHandle::class.java)
    }
}