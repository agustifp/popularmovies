package android.afebrerp.com.movies.data.entity

data class MovieListDTO(val page: Int?,
                        val total_results: Int?,
                        val total_pages: Int?,
                        val results: List<MovieDTO>)