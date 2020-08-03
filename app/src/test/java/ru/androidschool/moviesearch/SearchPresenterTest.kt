package ru.androidschool.moviesearch

import com.google.gson.annotations.SerializedName
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class SearchPresenterTest {

    // 1
    private lateinit var repository: MovieProvider
    private lateinit var presenter: SearchPresenter
    private lateinit var view: SearchPresenter.View

    // 2
    @Before
    fun setup() {
        // 3
        repository = mock()
        // 4
        presenter = SearchPresenter(repository)
        // 5
        view = mock()
        // 6
        presenter.attachView(view)
    }

    // 7
    @Test
    fun search_withEmptyQuery_callsShowQueryRequiredMessage() {
        // 8
        presenter.search("")

        // 9
        verify(view).showQueryRequiredMessage()
    }

    @Test
    fun search_callsShowLoading() {
        presenter.search("Spider-Man")
        verify(view).showLoading()
    }

    @Test
    fun search_callsSearchMovies() {
        presenter.search("Spider-Man")
        verify(repository).searchMovies(eq("Spider-Man"), any())
    }

    @Test
    fun search_withRepositoryHavingMovies_callsShowMoviesResults() {
        // 1
        val movie = Movie(false, "Some overview", "3.04.2020", 1, "AndroidSchool")
        val movies = listOf<Movie>(movie)

        // 2
        doAnswer {
            val callback: MovieProvider.RepositoryCallback<List<Movie>> = it.getArgument(1)
            callback.onSuccess(movies)
        }.whenever(repository).searchMovies(eq("android"), any())

        // 3
        presenter.search("android")

        // 4
        verify(view).showMoviesResults(eq(movies))
    }

    @Test
    fun search_whenRepositoryDontHaveMovies_callsShowEmptyMovies() {
        // 1
        val movies = listOf<Movie>()

        // 2
        doAnswer {
            val callback: MovieProvider.RepositoryCallback<List<Movie>> = it.getArgument(1)
            callback.onSuccess(movies)
        }.whenever(repository).searchMovies(eq("dfgrs"), any())

        // 3
        presenter.search("dfgrs")

        // 4
        verify(view).showEmptyMovies()
    }
}