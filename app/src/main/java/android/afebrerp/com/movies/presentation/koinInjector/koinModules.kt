package android.afebrerp.com.movies.presentation.koinInjector



import android.afebrerp.com.movies.data.net.MoviesAPI
import android.afebrerp.com.movies.data.net.RetrofitAdapter
import android.afebrerp.com.movies.data.service.PopularMoviesRepositoryImpl
import android.afebrerp.com.movies.data.service.dataSource.PopularMoviesDataStoreImpl
import android.afebrerp.com.movies.data.service.dataSource.PopularMoviesStore
import android.afebrerp.com.movies.domain.repository.PopularMoviesRepository
import android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies.PopularMoviesViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


//
//val netModule = module {
//    single { MockServer(androidContext()) }
//    single<RestApi> { RetrofitAdapter(mockServer = get()).createBackEndAdapter() }
//}
//
val repositoryModule = module {
    single<MoviesAPI> { RetrofitAdapter().createBackEndAdapter() }
    single<PopularMoviesStore> { PopularMoviesDataStoreImpl(moviesAPI = get()) }
    single<PopularMoviesRepository> { PopularMoviesRepositoryImpl(get()) }
}
//
//val useCaseModule = module {
//    factory { ImportantOperationUseCase(breedsRepository = get()) }
//    factory { BreedsListUseCase(breedsRepository = get()) }
//    factory { GetFavoriteBreedsDBUseCase(breedsRepository = get()) }
//    factory { IsFavoriteBreedUseCase(breedsRepository = get()) }
//    factory { SetFavoriteBreedUseCase(breedsRepository = get()) }
//    factory { RemoveFavoriteBreedUseCase(breedsRepository = get()) }
//}
//
//val useCaseWrapperModule = module {
//    factory { MainUseCaseWrapper(importantOperationUseCase = get()) }
//    factory { BreedsListUseCaseWrapper(breedsListUseCase = get(), importantOperationUseCase = get()) }
//    factory {
//        BreedDetailUseCaseWrapper(isFavoriteBreedUseCase = get(),
//                setFavoriteBreedUseCase = get(),
//                removeFavoriteBreedUseCase = get())
//    }
//    factory { BreedFavoriteListUseCaseWrapper(getFavoriteBreedsDBUseCase = get()) }
//}
//
//
val viewModelModules = module {
    viewModel { PopularMoviesViewModel(mainUseCaseWrapper = get()) }
}

val generalModules = listOf(repositoryModule,viewModelModules)