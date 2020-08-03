package ru.androidschool.moviesearch

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test


class RepositoryTests {
    private lateinit var spyRepository: MovieProvider
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        // 1
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        // 2
        spyRepository = spy(MoviesRepository(sharedPreferences))
    }

    @Test
    fun addFavorite_savesJsonMovie() {
        // 1
        doReturn(emptyList<Movie>()).whenever(spyRepository).getFavoriteMovies()

        // 2
        val movie = Movie(false, "Some overview", "3.04.2020", 1, "AndroidSchool")
        spyRepository.addFavorite(movie)

        // 3
        inOrder(sharedPreferencesEditor) {
            // 4
            val jsonString = Gson().toJson(listOf(movie))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()
        }
    }
}