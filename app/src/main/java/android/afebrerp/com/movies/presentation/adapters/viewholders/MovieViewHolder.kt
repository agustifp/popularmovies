package android.afebrerp.com.movies.presentation.adapters.viewholders

import android.afebrerp.com.movies.presentation.entities.MovieViewEntity
import android.afebrerp.com.movies.presentation.glideModule.GlideApp
import android.afebrerp.com.movies.presentation.util.Constants
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.afebrerp.movies.android.R
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(movieViewEntity: MovieViewEntity) {
        view.movieTitle.text = movieViewEntity.title
        val ratingString = "${view.context.getString(R.string.rating)} ${movieViewEntity.voteAverage}"
        view.movieRating.text = ratingString
        loadImage(movieViewEntity.imageUri)
    }

    private fun loadImage(imageUrl: String) {
        GlideApp
                .with(view)
                .load("${Constants.BASE_IMAGE_URL}$imageUrl")
                .placeholder(R.drawable.default_thumbnail_placeholder)
                .centerCrop()
                .into(view.movieImage)
    }

    fun clearAnimation() {
        view.clearAnimation()
    }
}