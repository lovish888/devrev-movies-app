package com.lovish888.devrev.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lovish888.devrev.movies.R
import com.lovish888.devrev.movies.adapter.MoviesListAdapter
import com.lovish888.devrev.movies.vm.MovieListVM

class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MovieListVM
    private lateinit var adapter: MoviesListAdapter
    private var tabType = "latest"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tabType = arguments?.getString("tabType") ?: "latest"

        // Setup RecyclerView and its Adapter
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = MoviesListAdapter { movieId ->
            val action = MoviesListTabFragmentDirections.actionMoviesListTabFragmentToMovieDetailFragment(movieId = movieId)
            findNavController().navigate(action)
        }
        recyclerView?.adapter = adapter

        // Setup ViewModel
        setupVM()

        return view
    }

    private fun setupVM() {
        viewModel = ViewModelProvider(this)[MovieListVM::class.java]
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        // Fetch Movies List
        viewModel.fetchMovies(tabType)
    }
}
