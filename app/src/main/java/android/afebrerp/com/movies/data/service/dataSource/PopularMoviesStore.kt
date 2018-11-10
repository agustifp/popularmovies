package android.afebrerp.com.movies.data.service.dataSource

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity


interface PopularMoviesStore {
    suspend fun getPopularMoviesList(page: Int): MovieListEntity

    suspend  fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity
}