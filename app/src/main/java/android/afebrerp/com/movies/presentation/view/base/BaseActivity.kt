package android.afebrerp.com.movies.presentation.view.base

import android.afebrerp.com.movies.data.reachability.ReachAbilityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afebrerp.movies.android.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity(), BaseActivityFragmentInterface {
    protected lateinit var currentTag: String
    protected lateinit var currentFragment: Fragment

    companion object {
        private const val CURRENT_FRAGMENT_TAG: String = "currentTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setToolbar()
        initializeFragmentAndTAG(savedInstanceState)
        beginTransaction()
        checkInternetState()
    }

    private fun checkInternetState() {
        when {
            !isInternetReachable() -> onConnectivityChanges(false)
        }
    }

    override fun onResume() {
        super.onResume()
        ReachAbilityManager.setBackOfficeReachAbleListener {
            onConnectivityChanges(it)
        }
    }

    override fun isInternetReachable(): Boolean = ReachAbilityManager.isConnected

    @CallSuper
    open fun onConnectivityChanges(isConnected: Boolean) {
        //set a general param.
        Log.d("BaseActvity: ", "onConnectivityChanges reached with isConnected: $isConnected")
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initializeFragmentAndTAG(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CURRENT_FRAGMENT_TAG)) {
            createFragmentAndSettingTAG()
        } else {
            savedInstanceState.getString(CURRENT_FRAGMENT_TAG)?.let { currentTag ->
                supportFragmentManager.getFragment(savedInstanceState, currentTag)?.let { it ->
                    currentFragment = it
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let { bundle ->
            supportFragmentManager.putFragment(bundle, currentTag, currentFragment)
        }
        outState?.putString(CURRENT_FRAGMENT_TAG, currentTag)
        super.onSaveInstanceState(outState)
    }

    private fun beginTransaction() =
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment, currentTag).commit()

    abstract fun createFragmentAndSettingTAG()

    protected fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }


    fun showSnackBar(message: String, view: View) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        snackBar.show()
    }
}