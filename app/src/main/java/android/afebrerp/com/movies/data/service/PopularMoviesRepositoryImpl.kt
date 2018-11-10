package android.afebrerp.com.movies.data.service

import android.afebrerp.com.movies.data.service.dataSource.PopularMoviesStore
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository

class PopularMoviesRepositoryImpl(private val popularMoviesStore: PopularMoviesStore) :
    PopularMoviesRepository {
    override suspend fun getPopularMovies(page: Int): MovieListEntity {
        return popularMoviesStore.getPopularMoviesList(page)
    }

    override suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity =
        popularMoviesStore.getSearchMoviesList(searchText, page)
}