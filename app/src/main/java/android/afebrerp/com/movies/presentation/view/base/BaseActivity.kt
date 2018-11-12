package android.afebrerp.com.movies.presentation.view.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afebrerp.movies.android.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity : AppCompatActivity() {
    protected var currentTag: String? = null
    protected var currentFragment: Fragment? = null

    companion object {
        private const val CURRENT_FRAGMENT_TAG: String = "currentTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setToolbar()
        initializeFragmentAndTAG(savedInstanceState)
        beginTransaction()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
    }


    private fun initializeFragmentAndTAG(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CURRENT_FRAGMENT_TAG)) {
            createFragmentAndSettingTAG()
        } else {
            currentTag = savedInstanceState.getString(CURRENT_FRAGMENT_TAG)
            currentFragment = supportFragmentManager.getFragment(savedInstanceState, currentTag!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        supportFragmentManager.putFragment(outState!!, currentTag!!, currentFragment!!)
        outState?.putString(CURRENT_FRAGMENT_TAG, currentTag)
        super.onSaveInstanceState(outState)
    }

    private fun beginTransaction() =
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment!!, currentTag).commit()

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