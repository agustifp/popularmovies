package android.afebrerp.com.movies.data.exception

import android.afebrerp.com.movies.domain.exception.BaseException
import android.util.Log

object ExceptionManager {

    private const val TAG = "ExceptionManager"

    fun manageError(exception: BaseException): String {
        Log.d(TAG, exception.message, exception)

        //this has to retrieve the message from database
        return exception.customMessage
    }
}