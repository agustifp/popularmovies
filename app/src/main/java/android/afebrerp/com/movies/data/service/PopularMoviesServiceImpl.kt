package android.afebrerp.com.movies.data.service

import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.repository.PopularMoviesService
import org.koin.standalone.KoinComponent

class PopularMoviesServiceImpl(private val popularMoviesRepository: PopularMoviesRepository) :
        PopularMoviesService, KoinComponent {

    override suspend fun getSavedMostPopularMovies(): MovieListEntity =
            popularMoviesRepository.getMostPopularMoviesLocal()

    override suspend fun getPopularMovies(page: Int): MovieListEntity {
        val mostPopularMoviesList = popularMoviesRepository.getPopularMoviesList(page)
        popularMoviesRepository.setMostPopularMoviesLocal(mostPopularMoviesList.moviesList)
        return mostPopularMoviesList
    }

    override suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity =
            popularMoviesRepository.getSearchMoviesList(searchText, page)
}