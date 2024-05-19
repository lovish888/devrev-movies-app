package com.lovish888.devrev.movies.adapter

import com.lovish888.devrev.movies.databinding.MovieItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lovish888.devrev.movies.types.Movie

class MoviesListAdapter(
    private var movies: List<Movie>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], itemClickListener)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: OnItemClickListener) {
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
//                .placeholder(R.drawable.placeholder)
                .into(binding.moviePoster)

            binding.root.setOnClickListener { clickListener.onItemClick(movie) }
        }
    }
}
