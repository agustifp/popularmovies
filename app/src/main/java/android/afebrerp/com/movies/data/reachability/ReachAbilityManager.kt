package android.afebrerp.com.movies.data.reachability

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


object ReachAbilityManager : BroadcastReceiver(), KoinComponent {

    private val context: Context by inject()

    /**Var to evaluate if the network is connected or not*/
    var isConnected = true

    init {
        registerReceiver()
        isConnected = initialInternetState()
    }

    private fun registerReceiver() {
        context.registerReceiver(this, IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION))
    }

    /**@Override method to receive connectivity changes from system Broadcast */
    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras
        val networkInfo = extras?.getParcelable<NetworkInfo>("networkInfo")

        if (networkInfo?.detailedState == NetworkInfo.DetailedState.CONNECTED) {
            if (!isConnected) {
                isConnected = true
                internetReachAbilityListener(isConnected)
            }
        } else if (networkInfo?.detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
            if (isConnected) {
                isConnected = false
                internetReachAbilityListener(isConnected)
            }
        }
    }


    private lateinit var internetReachAbilityListener: (Boolean) -> Unit

    /**
     * @param block function to set and later on answer the state changes
     * */
    fun setBackOfficeReachAbleListener(block: (Boolean) -> Unit) {
        internetReachAbilityListener = block
    }

    private fun initialInternetState(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}