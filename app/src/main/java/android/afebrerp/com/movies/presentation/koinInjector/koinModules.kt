package android.afebrerp.com.movies.presentation.koinInjector


import android.afebrerp.com.movies.data.net.MoviesAPI
import android.afebrerp.com.movies.data.net.RetrofitAdapter
import android.afebrerp.com.movies.data.persistence.dao.PopularMoviesDAO
import android.afebrerp.com.movies.data.repository.PopularMoviesRepositoryImpl
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository
import android.afebrerp.com.movies.domain.usecases.GetPopularMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSearchMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.wrappers.MainUseCaseWrapper
import android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies.PopularMoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val daoModule = module {
    single { PopularMoviesDAO() }
}

val netModule = module {
    single<MoviesAPI> { RetrofitAdapter.createMoviesApiRetrofit() }
}

val repositoryModule = module {
    single<PopularMoviesRepository> { PopularMoviesRepositoryImpl(moviesAPI = get(), popularMoviesDAO = get()) }
}

val useCaseModule = module {
    factory { GetPopularMoviesUseCase(popularMoviesService = get()) }
    factory { GetSearchMoviesUseCase(popularMoviesService = get()) }
}

val useCaseWrapperModule = module {
    factory { MainUseCaseWrapper(getPopularMoviesUseCase = get(), getSearchMoviesUseCase = get()) }
}

val viewModelModules = module {
    viewModel { PopularMoviesViewModel(mainUseCaseWrapper = get()) }
}

val generalModules = listOf(repositoryModule, useCaseModule, useCaseWrapperModule, daoModule, netModule, viewModelModules)