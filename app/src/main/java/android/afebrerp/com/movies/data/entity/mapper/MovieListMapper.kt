package android.afebrerp.com.movies.data.entity.mapper

import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.data.entity.MovieDTO
import android.afebrerp.com.movies.data.entity.MovieListDTO


object MovieListMapper {
    fun toDomainObject(movieListDTO: MovieListDTO): MovieListEntity =
        MovieListEntity(true, movieListDTO.page ?: 0,
            movieListDTO.total_pages ?: 0,
            movieListDTO.results
                .filter { it.id != null }
                .map {
                    movieEntityFromMovieDTO(it)
                })

    private fun movieEntityFromMovieDTO(movieDTO: MovieDTO): MovieEntity {
        return MovieEntity(
            movieDTO.id ?: 0,
            movieDTO.video ?: false,
            movieDTO.vote_average ?: 0.0,
            movieDTO.title ?: "",
            movieDTO.popularity ?: 0.0,
            movieDTO.poster_path ?: "",
            movieDTO.genre_ids ?: ArrayList(),
            movieDTO.backdrop_path ?: "",
            movieDTO.adult ?: false,
            movieDTO.overview ?: "",
            movieDTO.release_date ?: ""
        )
    }
}
