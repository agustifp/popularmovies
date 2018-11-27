package android.afebrerp.com.movies.domain.repository

import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity


interface PopularMoviesRepository {
    //REALM
    suspend fun getMostPopularMoviesLocal(): MovieListEntity?

    suspend fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>)
    //API
    suspend fun getPopularMoviesList(page: Int): MovieListEntity

    suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity
}