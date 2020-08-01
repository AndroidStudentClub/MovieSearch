package ru.androidschool.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*


// MoviesListFragment реализует интерфейс MoviesListPresenter.View
class MoviesListFragment : Fragment(), MoviesListPresenter.View {

    // Инициализируем MoviesListPresenter
    private val presenter: MoviesListPresenter by lazy { MoviesListPresenter(MoviesRepository.getRepository(requireContext())) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициалищзируем view
        presenter.attachView(this)

        // Показываем сохранённые фильмы
        presenter.getFavoriteMovies()


        // Переход на поиск
        fab.setOnClickListener {
            presenter.openSearchScreen()
        }
    }

    // Методы MoviesListPresenter.View

    // Показываем сохранённые фильмы
    override fun showFavoriteMovies(movies: List<Movie>) {
        movies_recycler_view.adapter = MoviesAdapter(movies, R.layout.list_item_movie)
        movies_recycler_view.visibility = View.VISIBLE
        favorite_empty_placeholder.visibility = View.GONE
    }

    override fun openSearch() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun showEmptyMovies() {
        // Показываем плэйсхолдер если фильмов нет
        movies_recycler_view.visibility = View.GONE
        favorite_empty_placeholder.visibility = View.VISIBLE

        // Переход на поиск
        view?.findViewById<Button>(R.id.button_first)?.setOnClickListener {
            presenter.openSearchScreen()
        }
    }

    // Метод для удаления ссылки на view
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}