package ru.androidschool.moviesearch

interface MovieProvider {

    fun addFavorite(item: Movie)
    fun removeFavorite(item: Movie)
    fun saveFavorites(favorites: List<Movie>)
    fun getFavoriteMovies(): List<Movie>
    fun isFavorite(movie: Movie): Boolean
    fun searchMovies(query: String, callback: RepositoryCallback<List<Movie>>)

    interface RepositoryCallback<in T> {
        fun onSuccess(t: T?)
        fun onError()
    }
}
