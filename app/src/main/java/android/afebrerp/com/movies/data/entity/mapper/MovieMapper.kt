package android.afebrerp.com.movies.data.entity.mapper

import android.afebrerp.com.movies.data.entity.MovieDTO
import android.afebrerp.com.movies.data.entity.MovieListDTO
import android.afebrerp.com.movies.data.entity.realmentity.GenreRealmEntity
import android.afebrerp.com.movies.data.entity.realmentity.MovieRealmEntity
import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import io.realm.RealmList


object MovieMapper {

    fun toDomainObject(movieListDTO: MovieListDTO?): MovieListEntity =
            if (movieListDTO != null) MovieListEntity(movieListDTO.page ?: 0,
                    movieListDTO.total_pages ?: 0,
                    movieListDTO.results
                            .filter { it.id != null }
                            .map {
                                movieEntityFromMovieDTO(it)
                            }, true)
            else MovieListEntity(-1, -1, listOf(), false)

    fun toDomainObjectWithSearch(searchText: String, movieListDTO: MovieListDTO?): MovieListEntity =
            if (movieListDTO != null) MovieListEntity(movieListDTO.page ?: 0,
                    movieListDTO.total_pages ?: 0,
                    movieListDTO.results
                            .filter { it.id != null }
                            .map {
                                movieEntityFromMovieDTO(it)
                            }, searchText, true)
            else MovieListEntity(-1, -1, listOf(), false)

    private fun movieEntityFromMovieDTO(movieDTO: MovieDTO): MovieEntity {
        return MovieEntity(movieDTO.id ?: 0,
                movieDTO.video ?: false,
                movieDTO.vote_average ?: 0.0,
                movieDTO.title ?: "",
                movieDTO.popularity ?: 0.0,
                movieDTO.poster_path ?: "",
                movieDTO.genre_ids ?: ArrayList(),
                movieDTO.backdrop_path ?: "",
                movieDTO.adult ?: false,
                movieDTO.overview ?: "",
                movieDTO.release_date ?: "")
    }

    fun toDomainObject(movieRealmEntityList: List<MovieRealmEntity>?): MovieListEntity? =
            MovieListEntity(-1, -1, movieRealmEntityList?.map { movie ->
                MovieEntity(movie.id!!,
                        movie.video!!,
                        movie.voteAverage!!,
                        movie.title!!,
                        movie.popularity!!,
                        movie.posterPath!!,
                        movie.genreIds?.map { it.id!! } ?: listOf(),
                        movie.backdropPath!!,
                        movie.adult!!,
                        movie.overview!!,
                        movie.releaseDate!!)
            } ?: listOf())

    fun createMovieRealmEntityFromMovieEntity(movie: MovieEntity): MovieRealmEntity =
            MovieRealmEntity().apply {
                id = movie.id
                video = movie.video
                voteAverage = movie.voteAverage
                title = movie.title
                popularity = movie.popularity
                posterPath = movie.posterPath
                backdropPath = movie.backdropPath
                adult = movie.adult
                overview = movie.overview
                releaseDate = movie.releaseDate
                genreIds = RealmList<GenreRealmEntity>().apply {
                    movie.genreIds.forEach { genreId ->
                        GenreRealmEntity().apply {
                            id = genreId
                        }
                    }
                }
            }

}
