package com.lovish888.devrev.movies.types

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double
)