package com.lovish888.devrev.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.lovish888.devrev.movies.R
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.util.IMAGE_BASE_URL

class MoviesListAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(itemView: View, val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        private val moviesReleaseDate = itemView.findViewById<TextView>(R.id.movie_release_date)
        private val moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)
        private var currentMovie: Movie? = null

        init {
            itemView.setOnClickListener {
                currentMovie?.let {
                    onClick(it.id)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            movieTitle.text = movie.title
            moviesReleaseDate.text = movie.releaseDate
            Glide.with(itemView.context)
                .load("${IMAGE_BASE_URL}${movie.posterPath}")
                .apply(
                    RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .placeholder(R.drawable.movie_placeholder)
                )
                .into(moviePoster)
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
