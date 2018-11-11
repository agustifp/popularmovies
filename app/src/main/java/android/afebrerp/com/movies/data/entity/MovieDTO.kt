package android.afebrerp.com.movies.data.entity

data class MovieDTO(val vote_count: Int?,
                    val id: Int?,
                    val video: Boolean?,
                    val vote_average: Double?,
                    val title: String?,
                    val popularity: Double?,
                    val poster_path: String?,
                    val genre_ids: List<Int>?,
                    val backdrop_path: String?,
                    val adult: Boolean?,
                    val overview: String?,
                    val release_date: String?)