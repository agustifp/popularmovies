package android.afebrerp.com.movies.presentation.view.popularmovies

import android.afebrerp.com.movies.data.reachability.NetworkUtil
import android.afebrerp.com.movies.presentation.view.base.BaseActivity
import android.afebrerp.com.movies.presentation.view.base.BaseActivityFragmentInterface
import android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies.PopularMoviesFragment
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.afebrerp.movies.android.R
import kotlinx.android.synthetic.main.activity_base.*

class MostPopularMoviesActivity : BaseActivity(), BaseActivityFragmentInterface {

    override fun createFragmentAndSettingTAG() {
        currentFragment = PopularMoviesFragment.newInstance()
        currentTag = PopularMoviesFragment.TAG
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setTitle(R.string.app_name)
    }

    override fun getToolbar(): Toolbar = findViewById(R.id.toolbar)

    override fun showMessage(message: String) {
        showSnackBar(message, coordinator_main)
    }

    override fun onConnectivityChanges(isConnected: Boolean) {
        super.onConnectivityChanges(isConnected)
        val message :String = if(isConnected){
            getString(R.string.internet_connected)
        }else{
            getString(R.string.no_internet_connected)
        }
        showMessage(message)
    }
}
