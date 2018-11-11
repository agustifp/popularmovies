package android.afebrerp.com.movies.data.service.dataSource

import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity


interface PopularMoviesStore {
    fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>)
    suspend fun getPopularMoviesList(page: Int): MovieListEntity
    suspend  fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity
}