package ru.androidschool.moviesearch

// Используется MovieProvider
class SearchPresenter(private val repository: MovieProvider) : BasePresenter<SearchPresenter.View>() {

    private var movies: List<Movie>? = null

    fun search(query: String) {
        if (query.trim().isBlank()) {
            view?.showQueryRequiredMessage()
        } else {
            findMovies(query)
        }
    }

    // Поменяли MovieProvider.RepositoryCallback
    fun findMovies(query: String) {
        repository
            .searchMovies(query, object : MovieProvider.RepositoryCallback<List<Movie>> {
                override fun onSuccess(movies: List<Movie>?) {
                    if (movies != null && movies.isNotEmpty()) {
                        view?.showMoviesResults(movies)
                    } else {
                        view?.showEmptyMovies()
                    }
                }

                override fun onError() {
                    view?.showEmptyMovies()
                }
            })
    }

    interface View {
        fun showQueryRequiredMessage()
        fun showLoading()
        fun showMoviesResults(movies: List<Movie>)
        fun showEmptyMovies()
        fun refreshFavoriteStatus(movieId: Int)
    }
}