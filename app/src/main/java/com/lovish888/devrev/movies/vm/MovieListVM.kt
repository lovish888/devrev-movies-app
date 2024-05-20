package com.lovish888.devrev.movies.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lovish888.devrev.movies.types.Movie
import com.lovish888.devrev.movies.types.MovieListResponse
import com.lovish888.devrev.movies.util.GET_LATEST_MOVIES_URL
import com.lovish888.devrev.movies.util.GET_POPULAR_MOVIES_URL
import com.lovish888.devrev.networking.ApiRequest

class MovieListVM: ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    private val _currentPage = MutableLiveData<Int>()
    val movies: LiveData<List<Movie>> = _movies

    fun fetchMovies(type: String) {
        val url = if (type == "latest") GET_LATEST_MOVIES_URL else GET_POPULAR_MOVIES_URL
        ApiRequest.get<MovieListResponse>(
            url,
            onSuccess = {
                Log.d("Filter", "Got movies - ${it.results.size}")
                _movies.postValue(it.results)
                _currentPage.postValue(it.page)
            },
            onFailure = {
                Log.d("Filter", "Error fetching movies: ${it.message}")
            }
        )
    }

}