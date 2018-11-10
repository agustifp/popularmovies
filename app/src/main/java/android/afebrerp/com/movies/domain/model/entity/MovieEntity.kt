package android.afebrerp.com.movies.domain.model.entity


data class MovieEntity(val id: Int,
                       val video: Boolean,
                       val voteAverage: Double,
                       val title: String,
                       val popularity: Double,
                       val posterPath: String,
                       val genreIds: List<Int>,
                       val backdropPath: String,
                       val adult: Boolean,
                       val overview: String,
                       val releaseDate: String)