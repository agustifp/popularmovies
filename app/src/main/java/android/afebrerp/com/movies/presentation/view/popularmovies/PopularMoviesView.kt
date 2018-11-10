package android.afebrerp.com.movies.presentation.view.popularmovies

import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity

interface PopularMoviesView {
    fun setItems(moviesList: List<BaseListViewEntity>)

    fun showProgressBar(show: Boolean)

    fun showRecyclerView()

    fun hideRecyclerView()

    fun hideEmptyView()

    fun showEmptyView()

    fun setLoadingState(state: Boolean)

    fun restartListAnimation()

}