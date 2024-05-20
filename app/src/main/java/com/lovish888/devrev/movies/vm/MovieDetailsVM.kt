package com.lovish888.devrev.movies.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lovish888.devrev.movies.types.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lovish888.devrev.movies.util.GET_MOVIE_DETAILS_URL
import com.lovish888.devrev.networking.ApiRequest

class MovieDetailsVM : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun fetchMovieDetails(movieId: Int) {
        val url = GET_MOVIE_DETAILS_URL.replace("movie_id", movieId.toString())
        ApiRequest.get<Movie>(
            url,
            onSuccess = {
                _movie.postValue(it)
            },
            onFailure = {
                Log.d("Filter", "Error fetching movie details: ${it.message}")
            }
        )
    }
}
