package ru.androidschool.moviesearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_button.setOnClickListener {
            // 1
            val query = search_edit_text.text.toString().trim()
            // 2
            if (query.isBlank()) {
                // 3
                Snackbar.make(view, getString(R.string.search_alert), Snackbar.LENGTH_LONG).show()
            } else {
                // 4
                findMovies(query, view.context)
            }
        }
    }

    fun findMovies(query: String, context: Context) {
        MoviesRepository.getRepository(context)
            .searchMovies(query, object : MoviesRepository.RepositoryCallback<List<Movie>> {
                override fun onSuccess(movies: List<Movie>?) {
                    if (movies != null && movies.isNotEmpty()) {
                        showMovies(movies)
                    } else {
                        showEmptyMovies()
                    }
                }

                override fun onError() {
                    showEmptyMovies()
                }
            })
    }

    fun showEmptyMovies() {
        results_recycler_view.visibility = View.GONE
        no_results_placeholder.visibility = View.VISIBLE
    }

    fun showMovies(movies: List<Movie>) {
        results_recycler_view.adapter = MoviesAdapter(movies, R.layout.list_item_movie)
        results_recycler_view.visibility = View.VISIBLE
        no_results_placeholder.visibility = View.GONE
    }
}