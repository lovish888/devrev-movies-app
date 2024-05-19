package com.lovish888.devrev.movies.viewmodel

import androidx.lifecycle.ViewModel
import com.lovish888.devrev.movies.types.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lovish888.devrev.movies.types.MovieListResponse
import com.lovish888.devrev.movies.util.GET_MOVIE_DETAILS_URL
import com.lovish888.devrev.movies.util.GET_POPULAR_MOVIES_URL
import com.lovish888.devrev.movies.util.MOVIE_DB_API_KEY
import com.lovish888.devrev.networking.ApiRequest

class MovieDetailsVM : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun fetchMovieDetails() {
        ApiRequest.get<Movie>(
            GET_MOVIE_DETAILS_URL.replace("movie_id", movie.value?.id.toString()),
            onSuccess = {
                _movie.postValue(it)
            },
            onFailure = {
                print("Error fetching movie details: ${it.message}")
            }
        )
    }
}
