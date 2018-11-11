package android.afebrerp.com.movies.domain.usecases

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.params.PopularMoviesParams
import android.afebrerp.com.movies.domain.repository.PopularMoviesService
import android.afebrerp.com.movies.domain.usecases.base.BaseUseCase


class GetPopularMoviesUseCase (private val popularMoviesService: PopularMoviesService)
    : BaseUseCase<MovieListEntity, PopularMoviesParams>() {

    companion object {
        const val TAG = "GetPopularMoviesUseCase"
    }

    override fun getTag(): String = TAG

    override suspend fun buildRepoCall(params: PopularMoviesParams): MovieListEntity {
     val test =   popularMoviesService.getPopularMovies(params.page)
        return test
    }

}