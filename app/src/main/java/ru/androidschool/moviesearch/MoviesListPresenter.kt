package ru.androidschool.moviesearch

class MoviesListPresenter(private val repository: MoviesRepository) : BasePresenter<MoviesListPresenter.View>() {

    fun getFavoriteMovies() {
        val movies = repository.getFavoriteMovies()
        if (movies.isNotEmpty()) {
            view?.showFavoriteMovies(movies)
        } else {
            view?.showEmptyMovies()
        }
    }

    fun openSearchScreen() {
        view?.openSearch()
    }

    interface View {
        fun showFavoriteMovies(movies: List<Movie>)
        fun openSearch()
        fun showEmptyMovies()
    }
}



