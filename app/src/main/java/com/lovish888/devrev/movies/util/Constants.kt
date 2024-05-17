package com.lovish888.devrev.movies.util

const val MOVIE_DB_API_KEY = "909594533c98883408adef5d56143539"
const val GET_POPULAR_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?api_key=${MOVIE_DB_API_KEY}&language=en-US&sort_by=popularity.desc"
const val GET_LATEST_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?api_key=${MOVIE_DB_API_KEY}&language=en-US&sort_by=primary_release_date.desc"
const val GET_MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/discover/movie?{movie_id}?api_key=${MOVIE_DB_API_KEY}&language=en-US"



