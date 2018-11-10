package android.afebrerp.com.movies.domain.model.entity

import android.afebrerp.com.movies.domain.model.entity.base.BaseEntity


data class MovieListEntity(
    val resultResponse: Boolean,
    val page: Int,
    val totalPages: Int,
    val moviesList: List<MovieEntity>
) : BaseEntity(resultResponse)