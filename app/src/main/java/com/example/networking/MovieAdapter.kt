package com.example.networking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Добавьте этот импорт
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions // Для анимации

class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.movieTitle)
        private val rating: TextView = itemView.findViewById(R.id.movieRating)
        private val poster: ImageView = itemView.findViewById(R.id.moviePoster) // Добавлено

        fun bind(movie: Movie) {
            title.text = movie.title
            rating.text = "★ ${movie.vote_average}"

            // Загрузка изображения с помощью Glide
            val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            Glide.with(itemView.context)
                .load(posterUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // Плавное появление
                .placeholder(R.drawable.placeholder) // Заглушка во время загрузки
                .error(R.drawable.error) // Заглушка при ошибке
                .into(poster)
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}