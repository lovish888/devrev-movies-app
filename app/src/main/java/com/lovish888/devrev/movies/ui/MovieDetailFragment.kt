package com.lovish888.devrev.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lovish888.devrev.movies.R
import com.lovish888.devrev.movies.databinding.FragmentMovieDetailBinding
import com.lovish888.devrev.movies.databinding.FragmentMoviesListTabBinding
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.viewmodel.MovieDetailsVM

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsVM
    private lateinit var binding: FragmentMovieDetailBinding

    companion object {
        private const val ARG_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putInt(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieDetailsVM::class.java]

        val movieId = arguments?.getInt(ARG_MOVIE_ID)
        if (movieId != null) {
            viewModel.fetchMovieDetails()
        }

        viewModel.movie.observe(viewLifecycleOwner, Observer { movie ->
            if (movie != null) {
                displayMovieDetails(movie)
            }
        })
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
//            .placeholder(R.drawable.placeholder)
            .into(binding.moviePoster)
    }
}
