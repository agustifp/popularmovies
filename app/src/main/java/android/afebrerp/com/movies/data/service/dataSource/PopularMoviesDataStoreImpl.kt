package android.afebrerp.com.movies.data.service.dataSource

import android.afebrerp.com.movies.data.entity.mapper.MovieListMapper
import android.afebrerp.com.movies.data.net.MoviesAPI
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity

class PopularMoviesDataStoreImpl(private val moviesAPI: MoviesAPI) :
    PopularMoviesStore {
    override suspend fun getPopularMoviesList(page: Int): MovieListEntity =
        MovieListMapper.toDomainObject(moviesAPI.getPopularMoviesList(page).await())


    override suspend fun getSearchMoviesList(searchText: String, page: Int): MovieListEntity =
        MovieListMapper.toDomainObject(moviesAPI.getSearchMovies(searchText, page).await())
}