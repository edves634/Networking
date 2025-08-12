package com.example.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Объект singleton для работы с Retrofit
object RetrofitClient {
    // Базовый URL API TMDB
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    // API ключ для аутентификации (должен быть заменен на реальный)
    private const val API_KEY = "API_KEY" // Замените на реальный ключ

    // Инициализация логгера для HTTP-запросов
    private val logger = HttpLoggingInterceptor().apply {
        // Уровень логирования: BASIC (заголовки + метаданные)
        level = HttpLoggingInterceptor.Level.BASIC
    }

    // Создание HTTP-клиента с настроенным логгером
    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)  // Добавление интерсептора для логирования
        .build()

    // Построение Retrofit экземпляра
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)       // Установка базового URL
        .client(client)          // Подключение кастомного OkHttp клиента
        .addConverterFactory(GsonConverterFactory.create())  // Конвертер JSON в объекты
        .build()

    // Создание реализации API сервиса
    val tmdbService: TMDbApiService = retrofit.create(TMDbApiService::class.java)

    // Публичный метод для получения API ключа
    fun getApiKey() = API_KEY
}