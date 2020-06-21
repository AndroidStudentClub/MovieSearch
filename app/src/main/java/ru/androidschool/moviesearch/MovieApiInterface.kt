package ru.androidschool.moviesearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {
    @GET("search/movie")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String =  BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String?,
        @Query("query") searchQuery: String?
    ): Call<MovieResponse?>?
}
