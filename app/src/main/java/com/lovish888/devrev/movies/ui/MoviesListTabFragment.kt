package com.lovish888.devrev.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lovish888.devrev.movies.R
import com.lovish888.devrev.movies.adapter.MoviesListAdapter
import com.lovish888.devrev.movies.databinding.FragmentMoviesListTabBinding
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.viewmodel.MovieListVM

class MoviesListTabFragment : Fragment(), MoviesListAdapter.OnItemClickListener {

    private lateinit var viewModel: MovieListVM
    private lateinit var adapter: MoviesListAdapter
    private lateinit var binding: FragmentMoviesListTabBinding

    companion object {
        private const val ARG_TAB_TYPE = "tab_type"

        fun newInstance(tabType: String): MoviesListTabFragment {
            val fragment = MoviesListTabFragment()
            val args = Bundle()
            args.putString(ARG_TAB_TYPE, tabType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListVM::class.java)

        setupRecyclerView()

        // Observe the live data from the ViewModel
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movies?.let { adapter.setMovies(it) }
        }

        // Load the movies based on the tab type (Latest/Popular)
        val tabType = arguments?.getString(ARG_TAB_TYPE)
        if (tabType != null) {
            viewModel.fetchMovies(tabType, 0)
        }
    }

    private fun setupRecyclerView() {
        adapter = MoviesListAdapter(emptyList(), this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
    }

    override fun onItemClick(movie: Movie) {
        // Handle movie item click to navigate to detail page
        val action = MoviesListTabFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}
