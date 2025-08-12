package com.example.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "ru-RU" // Язык по умолчанию
    ): Response<TopRatedResponse>
}