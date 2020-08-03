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
        // Показываем состояние загрузки
        view?.showLoading()
        repository
            .searchMovies(query, object : MovieProvider.RepositoryCallback<List<Movie>> {
                override fun onSuccess(movies: List<Movie>?) {
                    // Скрываем состояние загрузки
                    view?.hideLoading()
                    if (movies != null && movies.isNotEmpty()) {
                        view?.showMoviesResults(movies)
                    } else {
                        view?.showEmptyMovies()
                    }
                }

                override fun onError() {
                    // Скрываем состояние загрузки
                    view?.hideLoading()
                    view?.showEmptyMovies()
                }
            })
    }

    interface View {
        fun showQueryRequiredMessage()
        fun showLoading()
        fun hideLoading()
        fun showMoviesResults(movies: List<Movie>)
        fun showEmptyMovies()
        fun refreshFavoriteStatus(movieId: Int)
    }
}