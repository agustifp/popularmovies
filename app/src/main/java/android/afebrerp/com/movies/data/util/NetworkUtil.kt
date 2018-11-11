package android.afebrerp.com.movies.data.util


import android.content.Context
import android.net.ConnectivityManager
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

object NetworkUtil :KoinComponent{
    private val context:Context by inject()

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}