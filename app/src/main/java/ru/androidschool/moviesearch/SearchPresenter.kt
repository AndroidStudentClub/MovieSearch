package ru.androidschool.moviesearch

class SearchPresenter(private val repository: MoviesRepository) : BasePresenter<SearchPresenter.View>() {

    private var movies: List<Movie>? = null

    fun search(query: String) {
        if (query.trim().isBlank()) {
            view?.showQueryRequiredMessage()
        } else {
            findMovies(query)
        }
    }

    fun findMovies(query: String) {
        repository
            .searchMovies(query, object : MoviesRepository.RepositoryCallback<List<Movie>> {
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