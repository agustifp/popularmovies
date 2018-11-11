package android.afebrerp.com.movies.data.persistence.extensions

import io.realm.Realm
import io.realm.RealmObject

fun <T : RealmObject> Realm.saveEntities(entityList: List<T>) {
    entityList.forEach {
        saveEntity(it)
    }
}

fun <T : RealmObject> Realm.saveEntity(entity: T) =
        copyToRealmOrUpdate(entity)

fun <T : RealmObject> Realm.getEntity(entityType: Class<T>, idField: String, id: Int): T? =
        where(entityType).equalTo(idField, id).findFirst()

fun <T : RealmObject, S> Realm.getAllEntities(entityType: Class<T>, block: (List<T>) -> S): S =
        block(where(entityType).findAll().toList())

fun <T : RealmObject> Realm.entityExists(entityType: Class<T>, idField: String, id: Int): Boolean =
        getEntity(entityType, idField, id) != null

fun <T : RealmObject> Realm.deleteEntity(entityType: Class<T>, id: Int) =
        where(entityType).equalTo("id", id).findFirst()?.deleteFromRealm()
