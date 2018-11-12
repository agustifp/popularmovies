package android.afebrerp.com.movies.data.service.repository

import android.afebrerp.com.movies.data.entity.mapper.MovieMapper
import android.afebrerp.com.movies.data.net.MoviesAPI
import android.afebrerp.com.movies.data.persistence.dao.PopularMoviesDAO
import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository

class PopularMoviesRepositoryImpl(private val moviesAPI: MoviesAPI, private val popularMoviesDAO: PopularMoviesDAO) :
        PopularMoviesRepository {

    override suspend fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>) =
            popularMoviesDAO.setMostPopularList(moviesList)

    //In this case, toDomainObject returns a non nullable vale.
    override suspend fun getMostPopularMoviesLocal(): MovieListEntity =
            popularMoviesDAO.getMostPopularList {
                MovieMapper.toDomainObject(it)
            }!!

    override suspend fun getPopularMoviesList(page: Int): MovieListEntity =
            MovieMapper.toDomainObject(moviesAPI.getPopularMoviesList(page).await())

    override suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity =
            MovieMapper.toDomainObjectWithSearch(searchText, moviesAPI.getSearchMovies(searchText, page).await())
}