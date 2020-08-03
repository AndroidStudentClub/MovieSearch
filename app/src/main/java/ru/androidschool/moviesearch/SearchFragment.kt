package ru.androidschool.moviesearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*

class SearchFragment : Fragment(), SearchPresenter.View {

    // Инициализируем SearchPresenter
    private val presenter: SearchPresenter by lazy { SearchPresenter(MoviesRepository.getRepository(requireContext())) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Передаём view для дальнейшего вызова методов
        presenter.attachView(this)
        search_button.setOnClickListener {
            // Вызываем метод презентера, который имеет всю логику
            presenter.search(search_edit_text.text.toString())
        }
    }

    // Метод для отображения UI сообщения пользователю
    override fun showQueryRequiredMessage() {
        Snackbar.make(requireView(), getString(R.string.search_alert), Snackbar.LENGTH_LONG).show()
    }

    // Метод для отображения UI загрузки
    override fun showLoading() {
        results_recycler_view.visibility = View.GONE
        progress.visibility = View.VISIBLE
        no_results_placeholder.visibility = View.GONE
    }

    // Метод для скрытия UI загрузки
    override fun hideLoading() {
        progress.visibility = View.GONE
        no_results_placeholder.visibility = View.VISIBLE
    }

    // Метод для отображения списка фильмов
    override fun showMoviesResults(movies: List<Movie>) {
        results_recycler_view.adapter = MoviesAdapter(movies, R.layout.list_item_movie)
        results_recycler_view.visibility = View.VISIBLE
        no_results_placeholder.visibility = View.GONE
    }

    // Метод для изменения и сохранения фильмов в список избранных
    override fun refreshFavoriteStatus(movieId: Int) {
    }

    // Метод для отображения плэйсхолдера
    override fun showEmptyMovies() {
        results_recycler_view.visibility = View.GONE
        no_results_placeholder.visibility = View.VISIBLE
    }

    // Метод для удаления ссылки на view
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}