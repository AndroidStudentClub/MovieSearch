package ru.androidschool.moviesearch

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call

import retrofit2.Callback

import retrofit2.Response


const val FAVORITES_KEY = "Favorites"

class MoviesRepository(private val sharedPreferences: SharedPreferences) {

    private val gson = Gson()

    fun addFavorite(item: Movie) {
        item.isFavorite = true
        val favorites = getFavoriteMovies() + item
        saveFavorites(favorites)
    }

    fun removeFavorite(item: Movie) {
        val favorites = getFavoriteMovies() - item
        saveFavorites(favorites)
    }

    private fun saveFavorites(favorites: List<Movie>) {
        val editor = sharedPreferences.edit()
        editor.putString(FAVORITES_KEY, gson.toJson(favorites))
        editor.apply()
    }

    private inline fun <reified T> Gson.fromJson(json: String): T =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    fun getFavoriteMovies(): List<Movie> {
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, null)
        if (favoritesString != null) {
            return gson.fromJson(favoritesString)
        }
        return emptyList()
    }

    fun isFavorite(movie: Movie): Boolean {
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, null)
        return if (!favoritesString.isNullOrEmpty()) {
            val movies: List<Movie> = gson.fromJson(favoritesString)
            movies.find { it.id == movie.id }?.isFavorite ?: false
        } else {
            false
        }
    }

    fun searchMovies(query: String, callback: RepositoryCallback<List<Movie>>) {
        val apiInterface = ApiClient.client?.create(MovieApiInterface::class.java)
        apiInterface?.getTopRatedMovies(language = "ru", searchQuery = query)
            ?.enqueue(object : Callback<MovieResponse?> {

                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    if (response != null && response.isSuccessful) {
                        val recipesContainer = response.body()
                        markFavorites(recipesContainer)
                        callback.onSuccess(recipesContainer?.results)
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    private fun markFavorites(movieContainer: MovieResponse?) {
        if (movieContainer != null) {
            val favoriteMovies = getFavoriteMovies()
            if (favoriteMovies.isNotEmpty()) {

                for (item in 0 until movieContainer.results?.size!!) {
                    val movie = movieContainer.results!![item]
                    val favoriteMovie = favoriteMovies.find { it.id == movie.id }
                    if (favoriteMovie != null) {
                        movie.isFavorite = true
                    }
                }
            }
        }
    }

    interface RepositoryCallback<in T> {
        fun onSuccess(t: T?)
        fun onError()
    }

    companion object {
        fun getRepository(context: Context): MoviesRepository {
            return MoviesRepository(
                context.getSharedPreferences(
                    FAVORITES_KEY,
                    Context.MODE_PRIVATE
                )
            )
        }
    }
}