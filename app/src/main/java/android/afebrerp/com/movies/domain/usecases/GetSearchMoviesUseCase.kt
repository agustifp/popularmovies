package android.afebrerp.com.movies.domain.usecases

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.params.SearchMoviesParams
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository
import android.afebrerp.com.movies.domain.usecases.base.BaseUseCase


class GetSearchMoviesUseCase(private val popularMoviesService: PopularMoviesRepository) :
        BaseUseCase<MovieListEntity, SearchMoviesParams>() {

    companion object {
        const val TAG = "GetSearchMoviesUseCase"
    }

    override fun getTag(): String = TAG

    override suspend fun buildRepoCall(params: SearchMoviesParams): MovieListEntity =
            popularMoviesService.getSearchMovies(params.searchText, params.page)
}