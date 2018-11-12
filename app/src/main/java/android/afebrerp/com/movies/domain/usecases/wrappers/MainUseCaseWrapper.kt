package android.afebrerp.com.movies.domain.usecases.wrappers

import android.afebrerp.com.movies.domain.usecases.GetPopularMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSavedMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSearchMoviesUseCase

class MainUseCaseWrapper(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        getSearchMoviesUseCase: GetSearchMoviesUseCase,
        getSavedMoviesUseCase: GetSavedMoviesUseCase
) : BaseUseCaseWrapper() {
    init {
        addUseCases(getPopularMoviesUseCase, getSearchMoviesUseCase, getSavedMoviesUseCase)
    }

}