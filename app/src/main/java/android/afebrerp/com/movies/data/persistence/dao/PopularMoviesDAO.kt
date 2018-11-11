package com.futureworkshops.codetest.android.data.persistence.dao

import com.futureworkshops.codetest.android.data.extensions.deleteEntity
import com.futureworkshops.codetest.android.data.extensions.entityExists
import com.futureworkshops.codetest.android.data.extensions.getAllEntities
import com.futureworkshops.codetest.android.data.extensions.saveEntity
import com.futureworkshops.codetest.android.data.model.dbmodel.BreedRealmEntity
import com.futureworkshops.codetest.android.data.persistence.db.RealmManager
import com.futureworkshops.codetest.android.domain.model.entity.BreedsListEntity


class BreedDAO {

    fun addFavoriteBreed(bredEntity: BreedRealmEntity) {
        RealmManager.executeTransaction { realm ->
            realm.saveEntity(bredEntity)
        }
    }

    fun getFavorites(block: (List<BreedRealmEntity>) -> BreedsListEntity): BreedsListEntity? =
            RealmManager.executeTransaction { realm ->
                realm.getAllEntities(BreedRealmEntity::class.java) {
                    block(it)
                }
            }

    fun removeFavorite(bredEntity: BreedRealmEntity) {
        RealmManager.executeTransaction { realm ->
            realm.deleteEntity(BreedRealmEntity::class.java, bredEntity.id!!.toInt())
        }
    }


    fun existInDB(bredEntity: BreedRealmEntity): Boolean = RealmManager.executeTransaction { realm ->
        realm.entityExists(BreedRealmEntity::class.java, BreedRealmEntity.ID, bredEntity.id!!.toInt())
    } ?: false

}