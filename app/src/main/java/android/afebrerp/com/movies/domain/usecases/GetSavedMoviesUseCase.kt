package android.afebrerp.com.movies.domain.usecases

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.params.EmptyParams
import android.afebrerp.com.movies.domain.repository.PopularMoviesService
import android.afebrerp.com.movies.domain.usecases.base.BaseUseCase


class GetSavedMoviesUseCase(private val moviesService: PopularMoviesService)
    : BaseUseCase<MovieListEntity, EmptyParams>() {

    companion object {
        const val TAG = "GetSavedMoviesUseCase"
    }

    override fun getTag(): String = TAG

    override suspend fun buildRepoCall(params: EmptyParams): MovieListEntity =
            moviesService.getSavedMostPopularMovies()
}
