package android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.params.PopularMoviesParams
import android.afebrerp.com.movies.domain.model.params.SearchMoviesParams
import android.afebrerp.com.movies.domain.usecases.GetPopularMoviesUseCase
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
        getMostPopularMovies()
        refreshList()
    }

    fun loadMoreMovies() {
        if (isSearching) {
            keepSearchingSameText()
        } else {
            getMostPopularMovies()
        }
    }

    fun searchMovieByText(newText: String) {
        isSearching = true
        currentPage = 1
        refreshList()
        if (newText != searchString) {
            searchString = newText
        }
        searchMostPopularMovies(searchString)
    }

    private fun keepSearchingSameText() {
        searchMostPopularMovies(searchString)
    }

    @Synchronized
    private fun getMostPopularMovies() {
        if (!loading) {
            loading = true
            execute(GetPopularMoviesUseCase::class, PopularMoviesParams(currentPage++), {
                if (it.result) {
                    manageMovieListEntityReceived(it as MovieListEntity)
                }
                loading = false
            }, {
                loading = false
                //manage error on view if needed
            })
        }
    }

    private fun refreshList() {
        popularMoviesList?.value?.clear()
    }

    private fun manageMovieListEntityReceived(moviesListEntity: MovieListEntity) {
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


    @Synchronized
    fun searchMostPopularMovies(searchString: String) {
        cancelJob(GetSearchMoviesUseCase::class)
        execute(GetSearchMoviesUseCase::class, SearchMoviesParams(currentPage, searchString), {
            if (searchString == (it as MovieListEntity).searchedString) {
                Log.d("ViewModel", "lastSearch == it.searchedString are equals, then show new response: ${it.result}")
                currentPage++
                if (it.result && isSearching) {
                    manageMovieListEntityReceived(it)
                }
            }
            loading = false
        }, {
            //manage error on view if needed
            loading = false
        })

    }
}