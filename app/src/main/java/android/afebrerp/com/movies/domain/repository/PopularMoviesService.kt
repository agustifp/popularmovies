package android.afebrerp.com.movies.domain.repository

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity

interface PopularMoviesService {

    suspend fun getPopularMovies(page: Int): MovieListEntity

    suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity

    suspend fun getSavedMostPopularMovies(): MovieListEntity
}