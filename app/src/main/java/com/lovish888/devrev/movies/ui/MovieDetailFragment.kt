package com.lovish888.devrev.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.lovish888.devrev.movies.databinding.FragmentMovieDetailBinding
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.vm.MovieDetailsVM

class MovieDetailFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsVM
    private lateinit var binding: FragmentMovieDetailBinding

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

        val movieId = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                displayMovieDetails(movie)
            }
        }

        Log.d("Filter", "Fetching movieId $movieId")
        viewModel.fetchMovieDetails(movieId)
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


