package ru.androidschool.moviesearch

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    var page: Int? = null,

    var results: List<Movie>? = null,

    @SerializedName("total_results")
    var totalResults: Int? = null,

    @SerializedName("total_pages")
    var totalPages: Int? = null
)
