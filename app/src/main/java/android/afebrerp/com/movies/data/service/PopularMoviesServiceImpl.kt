package android.afebrerp.com.movies.data.service

import android.afebrerp.com.movies.data.service.dataSource.PopularMoviesRepository
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.repository.PopularMoviesService
import android.content.Context
import android.net.ConnectivityManager

class PopularMoviesServiceImpl(private val popularMoviesRepository: PopularMoviesRepository, private val context: Context) :
        PopularMoviesService {
    override suspend fun getSavedMostPopularMovies(): MovieListEntity =
            popularMoviesRepository.getMostPopularMoviesLocal()

    override suspend fun getPopularMovies(page: Int): MovieListEntity {
        return if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null) {
            val mostPopularMoviesList = popularMoviesRepository.getPopularMoviesList(page)
            popularMoviesRepository.setMostPopularMoviesLocal(mostPopularMoviesList.moviesList)
            mostPopularMoviesList
        } else {
            popularMoviesRepository.getMostPopularMoviesLocal()
        }
    }

    override suspend fun getSearchMovies(searchText: String, page: Int): MovieListEntity =
            popularMoviesRepository.getSearchMoviesList(searchText, page)
}