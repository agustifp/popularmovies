package android.afebrerp.com.movies.domain.model.params

import android.afebrerp.com.movies.domain.model.params.base.BaseParams

data class SearchMoviesParams(val page: Int, val searchText: String) : BaseParams()
