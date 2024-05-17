package com.lovish888.devrev.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.types.MovieListResponse
import com.lovish888.devrev.movies.util.GET_POPULAR_MOVIES_URL
import com.lovish888.devrev.networking.ApiRequest

class MovieListViewModel: ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    private val _currentPage = MutableLiveData<Int>()
    val movies: LiveData<List<Movie>> = _movies
    val currentPage:  LiveData<Int> = _currentPage

    fun fetchMovies(type: String, page: Int) {
        ApiRequest.get<MovieListResponse>(
            GET_POPULAR_MOVIES_URL,
            onSuccess = {
                _movies.postValue(it.results)
                _currentPage.postValue(it.page)
            },
            onFailure = {
                print("Error fetching movies: ${it.message}")
            }
        )
    }

}