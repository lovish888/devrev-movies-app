package com.lovish888.devrev.movies.types

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0,
    val popularity: Double? = 0.0
)