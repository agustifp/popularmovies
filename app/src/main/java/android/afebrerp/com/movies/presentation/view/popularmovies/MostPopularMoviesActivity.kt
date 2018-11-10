package android.afebrerp.com.movies.presentation.view.popularmovies

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

}
