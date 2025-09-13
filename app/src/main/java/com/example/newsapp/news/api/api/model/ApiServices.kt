package com.example.newsapp.news.api.api.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServices {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun getWebServices() : WebServices{
    return retrofit.create(WebServices::class.java)

}


}