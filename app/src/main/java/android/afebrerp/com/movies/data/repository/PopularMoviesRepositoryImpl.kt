package android.afebrerp.com.movies.data.repository

import android.afebrerp.com.movies.data.entity.mapper.MovieMapper
import android.afebrerp.com.movies.data.net.MoviesAPI
import android.afebrerp.com.movies.data.persistence.dao.PopularMoviesDAO
import android.afebrerp.com.movies.data.reachability.ReachAbilityManager
import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository

class PopularMoviesRepositoryImpl(private val moviesAPI: MoviesAPI, private val popularMoviesDAO: PopularMoviesDAO) :
        PopularMoviesRepository {

    override suspend fun getPopularMovies(page: Int): MovieListEntity? {
        return if (ReachAbilityManager.isConnected) {
            val mostPopularMoviesList = getPopularMoviesList(page)
            setMostPopularMoviesLocal(mostPopularMoviesList.moviesList)
            mostPopularMoviesList
        } else {
            getMostPopularMoviesLocal()
        }
    }

    override suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity =
            getSearchMoviesList(searchText, page)

    private fun setMostPopularMoviesLocal(moviesList: List<MovieEntity>) =
            popularMoviesDAO.setMostPopularList(moviesList)


    //In this case, toDomainObject returns a non nullable vale.
    private fun getMostPopularMoviesLocal(): MovieListEntity? =
            popularMoviesDAO.getMostPopularList {
                MovieMapper.toDomainObject(it)
            }

    private suspend fun getPopularMoviesList(page: Int): MovieListEntity =
            MovieMapper.toDomainObject(moviesAPI.getPopularMoviesList(page).await())

    private suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity =
            MovieMapper.toDomainObjectWithSearch(searchText, moviesAPI.getSearchMovies(searchText, page).await())
}