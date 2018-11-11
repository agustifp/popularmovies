package android.afebrerp.com.movies.data.persistence.db

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object RealmManager : KoinComponent {

    private val androidContext: Context by inject()

    private const val REALM_KEY = "8ab5dd83af7c1b20452927c339eb7701e0d727c31682978c7c92a8193b11e595"

    init {
        Realm.init(androidContext)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("moviesRealmDatabase.realm")
                .schemaVersion(1)
                .encryptionKey(REALM_KEY.toByteArray())
                .build())
    }

    fun <T> executeTransaction(fn: (Realm) -> T): T? =
            openRealmInstance { realm ->
                var result: T? = null
                realm.executeTransaction { result = fn(it) }
                result
            }

    @Synchronized
    private fun <T> openRealmInstance(fn: (Realm) -> T): T {
        val realm = Realm.getDefaultInstance()
        realm.use {
            return fn(realm)
        }
    }
}