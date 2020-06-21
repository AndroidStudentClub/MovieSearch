package ru.androidschool.moviesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MoviesAdapter(
    private val movies: List<Movie>,
    private val rowLayout: Int
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private lateinit var moviesRepository: MoviesRepository

    class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var movieTitle: TextView = v.findViewById(R.id.title)
        internal var data: TextView = v.findViewById(R.id.subtitle)
        internal var movieDescription: TextView = v.findViewById(R.id.description)
        internal var rating: TextView = v.findViewById(R.id.rating)
        internal var preview: ImageView = v.findViewById(R.id.preview)
        internal var favoriteCheckBox: CheckBox = v.findViewById(R.id.favorite_checkbox)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        moviesRepository = MoviesRepository.getRepository(parent.context)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = movies[position]
        holder.movieTitle.text = current.title
        holder.data.text = current.releaseDate
        holder.movieDescription.text = current.overview
        holder.rating.text =
            holder.rating.context.getString(R.string.rating, current.voteAverage!!.toString())

        holder.favoriteCheckBox.isChecked = moviesRepository.isFavorite(movies[position])

        holder.favoriteCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                moviesRepository.addFavorite(movies[position])
            } else {
                moviesRepository.removeFavorite(movies[position])
            }
        }


        Picasso.with(holder.itemView.context)
            .load(current.getFullPath())
            .into(holder.preview)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}