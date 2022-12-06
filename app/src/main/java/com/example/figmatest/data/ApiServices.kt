package com.example.figmatest.data

import retrofit2.http.GET

interface ApiServices {

    @GET("/api/backgrounds/?group=video&category_id=1")
    suspend fun getItems(): List<ItemJson>

}