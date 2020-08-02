package ru.androidschool.moviesearch

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
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
}