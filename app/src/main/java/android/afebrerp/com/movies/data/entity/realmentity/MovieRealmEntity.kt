package android.afebrerp.com.movies.data.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class MovieRealmEntity : RealmObject() {
    companion object {
        const val ID = "id"
    }
    @PrimaryKey var id: Int? = null
    var video: Boolean? = null
    var voteAverage: Double? = null
    var title: String? = null
    var popularity: Double? = null
    var posterPath: String? = null
    var genreIds: RealmList<Int>? = null
    var backdropPath: String? = null
    var adult: Boolean? = null
    var overview: String? = null
    var releaseDate: String? = null
}
