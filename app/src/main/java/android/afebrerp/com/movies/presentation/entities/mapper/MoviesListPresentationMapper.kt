package android.afebrerp.com.movies.presentation.entities.mapper

import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.presentation.entities.MovieViewEntity
import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity

object MoviesListPresentationMapper {
    fun toPresentationObject(movieListEntity: MovieListEntity) : List<BaseListViewEntity> =
            movieListEntity.moviesList
                    .map { MovieViewEntity(it.title, it.voteAverage, it.backdropPath) as BaseListViewEntity }
}