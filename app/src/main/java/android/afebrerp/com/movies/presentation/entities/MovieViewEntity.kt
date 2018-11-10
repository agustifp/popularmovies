package android.afebrerp.com.movies.presentation.entities

import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity


data class MovieViewEntity (val title: String, val voteAverage: Double, val imageUri: String) : BaseListViewEntity()