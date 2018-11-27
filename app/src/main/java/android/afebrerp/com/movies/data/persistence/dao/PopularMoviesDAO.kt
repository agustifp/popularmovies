package android.afebrerp.com.movies.data.persistence.dao


import android.afebrerp.com.movies.data.entity.mapper.MovieMapper
import android.afebrerp.com.movies.data.entity.realmentity.MovieRealmEntity
import android.afebrerp.com.movies.data.persistence.db.RealmManager
import android.afebrerp.com.movies.data.persistence.extensions.getAllEntities
import android.afebrerp.com.movies.data.persistence.extensions.saveEntities
import android.afebrerp.com.movies.domain.model.entity.MovieEntity
import android.afebrerp.com.movies.domain.model.entity.MovieListEntity


class PopularMoviesDAO {

    fun getMostPopularList(block: (List<MovieRealmEntity>) -> MovieListEntity?): MovieListEntity? =
            RealmManager.executeTransaction { realm ->
                realm.getAllEntities(MovieRealmEntity::class.java) {
                    block(it)
                }
            }

    fun setMostPopularList(moviesList: List<MovieEntity>) {
        RealmManager.executeTransaction { realm ->
            realm.saveEntities(moviesList.asSequence()
                    .map {
                        MovieMapper.createMovieRealmEntityFromMovieEntity(it)
                    }.toList())
        }
    }

}