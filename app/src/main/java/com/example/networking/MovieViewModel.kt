package com.example.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun loadTopRatedMovies() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitClient.tmdbService.getTopRatedMovies(
                    apiKey = RetrofitClient.getApiKey()
                )

                if (response.isSuccessful) {
                    _movies.value = response.body()?.results ?: emptyList()
                    _error.value = ""
                } else {
                    _error.value = "Error ${response.code()}: ${response.errorBody()?.string()}"
                }
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}