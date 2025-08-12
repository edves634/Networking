package com.example.networking

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Интерфейс для работы с API The Movie Database (TMDb)
interface TMDbApiService {

    /**
     * Получение списка топ-рейтинговых фильмов
     *
     * @GET("movie/top_rated") - конечная точка API для топ-рейтинговых фильмов
     */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        // Параметр API ключа (обязательный)
        @Query("api_key") apiKey: String,

        /**
         * Параметр языка (необязательный, значение по умолчанию)
         *
         * @param language Язык возвращаемых данных (ru-RU - русский)
         * @Default значение "ru-RU"
         */
        @Query("language") language: String = "ru-RU"
    ): Response<TopRatedResponse> // Возвращает полный HTTP-ответ с оберткой
}