package com.afebrerp.movies.android

import android.afebrerp.com.movies.data.entity.MovieListDTO
import android.afebrerp.com.movies.data.entity.mapper.MovieMapper
import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity
import android.afebrerp.com.movies.domain.model.entity.base.BaseEntity
import com.afebrerp.movies.android.base.BaseTest
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Test


class MapperTest : BaseTest() {

    private fun getMockedJsonResponse(): JsonObject {

        val jsonResponse = JsonObject()
        jsonResponse.addProperty("page", 1)
        jsonResponse.addProperty("total_results", 19847)
        jsonResponse.addProperty("total_pages", 993)

        val jsonResponseMovieArray = JsonArray()
        val jsonMovie = JsonObject()
        jsonMovie.addProperty("vote_count", 2104)
        jsonMovie.addProperty("id", 335983)
        jsonMovie.addProperty("video", false)
        jsonMovie.addProperty("vote_average", 6.6)
        jsonMovie.addProperty("title", "Venom")
        jsonMovie.addProperty("popularity", 240.47)
        jsonMovie.addProperty("poster_path", "\\/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg")
        jsonMovie.addProperty("original_language", "en")
        jsonMovie.addProperty("original_title", "Venom")

        val jsonGenreArray = JsonArray()
        jsonGenreArray.add(878)
        jsonMovie.add("genre_ids", jsonGenreArray)
        jsonMovie.addProperty("backdrop_path", "\\/VuukZLgaCrho2Ar8Scl9HtV3yD.jpg")
        jsonMovie.addProperty("adult", false)
        jsonMovie.addProperty("overview", "When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego \"Venom\" to save his life.")
        jsonMovie.addProperty("release_date", "2018-10-03")
        jsonResponseMovieArray.add(jsonMovie)
        jsonResponse.add("results", jsonResponseMovieArray)
        return jsonResponse

    }

    /**Simple test to show a good procedure of a Mapper using a mocked Json.*/
    @Test
    fun movieToDomainObject() {

        val responseObject = Gson().fromJson(getMockedJsonResponse(), MovieListDTO::class.java)
        val movieListEntity = MovieMapper.toDomainObject(responseObject)
        //check class
        assert(movieListEntity is MovieListEntity)
        assert(movieListEntity is BaseEntity)
        val movieEntity = movieListEntity.moviesList[0]
        assert(movieEntity is MovieEntity)

        //check values movie list
        assert(movieListEntity.page == 1)
        assert(movieListEntity.totalPages == 993)

        //check values movieEntity
        assert(movieEntity.voteAverage == 6.6)
        assert(movieEntity.id == 335983)
        assert(!movieEntity.video)
        assert(movieEntity.title == "Venom")
        assert(movieEntity.popularity == 240.47)
        assert(movieEntity.posterPath == "\\/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg")
        assert(movieEntity.genreIds[0] == 878)
        assert(!movieEntity.adult)
        assert(movieEntity.overview == "When Eddie Brock acquires the powers of a symbiote, he will have to release his alter-ego \"Venom\" to save his life.")
        assert(movieEntity.releaseDate == "2018-10-03")

    }
}