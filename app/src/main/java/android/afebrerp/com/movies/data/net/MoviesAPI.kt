package android.afebrerp.com.movies.data.net

import android.afebrerp.com.movies.data.entity.MovieListDTO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {
    @GET("search/movie")
    fun getSearchMovies(
            @Query("query") searchText: String,
            @Query("page") page: Int): Deferred<MovieListDTO>

    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page: Int): Deferred<MovieListDTO>


}