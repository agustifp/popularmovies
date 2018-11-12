package android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies

import android.afebrerp.com.movies.data.util.NetworkUtil
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.params.EmptyParams
import android.afebrerp.com.movies.domain.model.params.PopularMoviesParams
import android.afebrerp.com.movies.domain.model.params.SearchMoviesParams
import android.afebrerp.com.movies.domain.usecases.GetPopularMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSavedMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.GetSearchMoviesUseCase
import android.afebrerp.com.movies.domain.usecases.wrappers.MainUseCaseWrapper
import android.afebrerp.com.movies.presentation.entities.FooterViewViewEntity
import android.afebrerp.com.movies.presentation.entities.MovieViewEntity
import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity
import android.afebrerp.com.movies.presentation.entities.mapper.MoviesListPresentationMapper
import android.afebrerp.com.movies.presentation.view.base.BaseViewModel
import android.util.Log
import androidx.lifecycle.MutableLiveData


class PopularMoviesViewModel(mainUseCaseWrapper: MainUseCaseWrapper) : BaseViewModel(mainUseCaseWrapper) {

    private var currentPage = 1
    val popularMoviesList = MutableLiveData<ArrayList<BaseListViewEntity>>().apply {
        value = ArrayList()
        start()
    }

    private var loading: Boolean = false
    private var searchString = ""
    var isSearching = false

    fun start() {
        currentPage = 1

        if (isSearching) {
            searchMovieByText(searchString, true)
        } else {
            if (NetworkUtil.isNetworkAvailable()) {
                getMostPopularMovies(true)
            } else {
                getSavedMovies()
            }
        }
    }

    fun loadMoreMovies() {
        if (isSearching) {
            searchMovieByText()
        } else {
            getMostPopularMovies()
        }
    }

    fun searchMovieByText(newText: String? = "", refreshList: Boolean = false) {
        isSearching = true
        if (newText != searchString) {
            searchString = newText ?: ""
        }
        if (refreshList) {
            currentPage = 1
        }
        getMostPopularMovies(searchString)
    }

    @Synchronized
    private fun getMostPopularMovies(refresh: Boolean = false) {
        if (!loading) {
            loading = true
            if (refresh) currentPage = 1
            execute(GetPopularMoviesUseCase::class, PopularMoviesParams(currentPage++), {
                if (it.result) {
                    manageMovieListEntityReceived(refresh, it as MovieListEntity)
                }
                loading = false
            }, {
                loading = false
                //manage error on view if needed
            })
        }
    }

    fun getSavedMovies() {
        execute(GetSavedMoviesUseCase::class, EmptyParams(), {
            if (it.result) {
                manageMovieListEntityReceived(true, it as MovieListEntity)
            }
            loading = false
        }, {
            loading = false
            //manage error on view if needed
        })
    }

    private fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {
        if (refreshList) {
            popularMoviesList.value?.clear()
        }
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToMoviesList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity) as ArrayList<BaseListViewEntity>)
        removeFooter()
        if (!isLastPage) {
            popularMoviesList.value?.add(FooterViewViewEntity())
        }
        popularMoviesList.postValue(popularMoviesList.value)
    }

    private fun addResultToMoviesList(moviesListResult: ArrayList<BaseListViewEntity>) {
        val oldList = arrayListOf<BaseListViewEntity>()
        oldList.addAll(popularMoviesList.value!!)
        oldList.addAll(moviesListResult)
        popularMoviesList.value?.clear()
        popularMoviesList.value?.addAll(oldList.distinctBy { (it as? MovieViewEntity)?.id })
    }

    var isLastPage: Boolean = false

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        popularMoviesList.value?.removeAll { it is FooterViewViewEntity }
    }

    private var lastSearch = ""

    @Synchronized
    fun getMostPopularMovies(searchString: String) {
        lastSearch = searchString
        if (searchString != "") {
            execute(GetSearchMoviesUseCase::class, SearchMoviesParams(currentPage, searchString), {

                if (lastSearch == (it as MovieListEntity).searchedString) {
                    Log.e("ViewModel", "lastSearch == it.searchedString are equals, then show new response: ${it.result}")
                    if (it.result) {
                        manageMovieListEntityReceived(true, it)
                        isSearching = false
                    }
                }
                loading = false

            }, {
                //manage error on view if needed
                loading = false
            })
        }
    }
}