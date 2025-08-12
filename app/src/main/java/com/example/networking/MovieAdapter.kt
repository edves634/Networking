package com.example.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter  // Базовый класс для адаптеров с DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Библиотека для загрузки изображений
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions // Анимации Glide

// Адаптер для RecyclerView, работающий с данными типа Movie
class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    // Создание ViewHolder при необходимости
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Инфлейт макета элемента списка
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        // Получение элемента по позиции и передача в ViewHolder
        holder.bind(getItem(position))
    }

    // Внутренний класс ViewHolder
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Привязка view-элементов из макета
        private val title: TextView = itemView.findViewById(R.id.movieTitle)
        private val rating: TextView = itemView.findViewById(R.id.movieRating)
        private val poster: ImageView = itemView.findViewById(R.id.moviePoster) // View для постера

        // Метод привязки данных Movie к view-элементам
        fun bind(movie: Movie) {
            title.text = movie.title
            rating.text = "★ ${movie.vote_average}"  // Форматирование рейтинга

            // Формирование URL постера
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            // Загрузка изображения с помощью Glide
            Glide.with(itemView.context)
                .load(posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Анимация перехода
                .placeholder(R.drawable.placeholder) // Заглушка на время загрузки
                .error(R.drawable.error) // Заглушка при ошибке загрузки
                .into(poster) // Указание ImageView для загрузки
        }
    }

    // Класс для сравнения элементов списка
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        // Проверка уникальности элементов по ID
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        // Проверка идентичности содержимого элементов
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}