package android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.usecases.wrappers.MainUseCaseWrapper
import android.afebrerp.com.movies.presentation.entities.FooterViewViewEntity
import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity
import android.afebrerp.com.movies.presentation.entities.mapper.MoviesListPresentationMapper
import android.afebrerp.com.movies.presentation.view.base.BaseViewModel
import androidx.lifecycle.MutableLiveData


class PopularMoviesViewModel(mainUseCaseWrapper: MainUseCaseWrapper) : BaseViewModel(mainUseCaseWrapper) {

    var currentPage = 1
    val popularMoviesList = MutableLiveData<ArrayList<BaseListViewEntity>>().apply {
        value = ArrayList()
    }
    val onDataReceived = MutableLiveData<Unit>()

    fun getMostPopularMovies(refresh: Boolean = false) {
        if (refresh) currentPage = 1

//        mainUseCaseWrapper.execute(PopularMoviesParams(currentPage++)) {
//            manageMovieListEntityReceived(refresh, it)
//            onDataReceived.postValue(Unit)
//        }
    }

    private fun manageMovieListEntityReceived(refreshList: Boolean, moviesListEntity: MovieListEntity) {

        if (refreshList) popularMoviesList.value?.clear()
        setIsLastPage(currentPage, moviesListEntity.totalPages)
        addResultToMoviesList(MoviesListPresentationMapper.toPresentationObject(moviesListEntity))
        removeFooter()
        if (!isLastPage) popularMoviesList.value?.add(FooterViewViewEntity())
    }

    private fun addResultToMoviesList(moviesListResult: List<BaseListViewEntity>) {
        popularMoviesList.value?.addAll(moviesListResult)
    }

    private var isLastPage: Boolean = false

    private fun setIsLastPage(page: Int, totalPages: Int) {
        isLastPage = page == totalPages
    }

    private fun removeFooter() {
        popularMoviesList.value?.removeAll { it is FooterViewViewEntity }
    }
}