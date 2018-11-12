package android.afebrerp.com.movies.domain.usecases.wrappers

import android.afebrerp.com.movies.domain.usecases.GetPopularMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSearchMoviesUseCase

class MainUseCaseWrapper(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        getSearchMoviesUseCase: GetSearchMoviesUseCase
) : BaseUseCaseWrapper() {
    init {
        addUseCases(getPopularMoviesUseCase, getSearchMoviesUseCase)
    }

}