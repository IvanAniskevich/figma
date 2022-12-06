package com.example.figmatest.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://dev.bgrem.deelvin.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val API_SERVICES: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}