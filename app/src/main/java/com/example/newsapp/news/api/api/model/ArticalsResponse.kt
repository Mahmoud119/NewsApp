package com.example.newsapp.news.api.api.model

import com.google.gson.annotations.SerializedName

data class ArticalsResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)