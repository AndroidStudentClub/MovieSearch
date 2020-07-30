package ru.androidschool.moviesearch

class SearchPresenter(private val repository: MoviesRepository) {
    // 1
    private var view: SearchPresenter.View? = null
    private var recipes: List<Movie>? = null

    // 2
    fun attachView(view: View) {
        this.view = view
    }

    // 3
    fun detachView() {
        this.view = null
    }

    // 4
    fun search(query: String) {
        // 5
        if (query.trim().isBlank()) {
            view?.showQueryRequiredMessage()
        } else {
            findMovies(query)
        }
    }

    // 6
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

    // 7
    interface View {
        fun showQueryRequiredMessage()
        fun showLoading()
        fun showMoviesResults(movies: List<Movie>)
        fun showEmptyMovies()
        fun refreshFavoriteStatus(movieId: Int)
    }
}