package com.example.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Для корутин в ViewModel
import kotlinx.coroutines.launch // Для запуска корутин

class MovieViewModel : ViewModel() {
    // Приватные MutableLiveData для внутреннего изменения
    private val _movies = MutableLiveData<List<Movie>>()
    // Публичные неизменяемые LiveData для наблюдения из UI
    val movies: LiveData<List<Movie>> = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Основной метод загрузки данных
    fun loadTopRatedMovies() {
        // Запуск корутины в scope ViewModel (автоматическая отмена при очистке ViewModel)
        viewModelScope.launch {
            _loading.value = true // Установка состояния загрузки

            try {
                // Вызов сетевого запроса через Retrofit
                val response = RetrofitClient.tmdbService.getTopRatedMovies(
                    apiKey = RetrofitClient.getApiKey()
                )

                // Обработка ответа
                if (response.isSuccessful) {
                    // Успешный ответ - обновляем список фильмов
                    _movies.value = response.body()?.results ?: emptyList()
                    _error.value = "" // Сбрасываем ошибку
                } else {
                    // Ошибка HTTP (404, 500 и т.д.)
                    _error.value = "Error ${response.code()}: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                // Сетевая ошибка или ошибка парсинга
                _error.value = "Network error: ${e.message}"
            } finally {
                // Снятие состояния загрузки в любом случае
                _loading.value = false
            }
        }
    }
}