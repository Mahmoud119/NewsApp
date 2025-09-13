package com.example.newsapp.news.api.api.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("/v2/top-headlines/sources")
    fun loadSources(@Query("apiKey")apiKey: String = "92173ea6aa234f7d8962400a980f48ff"): Call<SourceResponse>

    @GET("/v2/everything")
    fun loadArticals(@Query("apiKey")apiKey: String,
                     @Query("sources")sourceId: String
    ): Call<ArticalsResponse>

}