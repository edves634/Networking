package com.example.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Инициализация ViewModel через делегат 'viewModels' (автоматическое создание и привязка к жизненному циклу)
    private val viewModel: MovieViewModel by viewModels()

    // Адаптер для RecyclerView (поздняя инициализация)
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Настройка RecyclerView
        setupRecyclerView()

        // Подписка на изменения в ViewModel
        observeViewModel()

        // Запуск загрузки данных
        viewModel.loadTopRatedMovies()
    }

    private fun setupRecyclerView() {
        // Получение ссылки на RecyclerView из макета
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Инициализация адаптера
        adapter = MovieAdapter()

        // Назначение адаптера для RecyclerView
        recyclerView.adapter = adapter

        // Установка линейного менеджера компоновки
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        // Наблюдение за списком фильмов
        viewModel.movies.observe(this) { movies ->
            // Обновление данных в адаптере при изменении списка
            adapter.submitList(movies)
        }

        // Наблюдение за сообщениями об ошибках
        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                // Показ Toast при возникновении ошибки
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        // Наблюдение за состоянием загрузки
        viewModel.loading.observe(this) { isLoading ->
            val progressBar: ProgressBar = findViewById(R.id.progressBar)
            // Отображение/скрытие ProgressBar в зависимости от состояния загрузки
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}