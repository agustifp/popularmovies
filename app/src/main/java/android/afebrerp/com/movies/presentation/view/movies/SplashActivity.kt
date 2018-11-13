package android.afebrerp.com.movies.presentation.view.movies

import android.afebrerp.com.movies.presentation.navigation.Navigator
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private var DELAY: Long = 1000
    private var hasBeenPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executeMainWithDelay()
    }

    private fun executeMainWithDelay() {
        Handler().postDelayed({
            if (!hasBeenPaused) {
                Navigator.openMainActivity(this)
                finish()
            }
        }, DELAY)
    }


    override fun onStop() {
        super.onStop()
        hasBeenPaused = true
    }

    override fun onPause() {
        super.onPause()
        hasBeenPaused = true
    }

    override fun onResume() {
        super.onResume()
        if (hasBeenPaused) {
            hasBeenPaused = false
            DELAY = 0
            Navigator.openMainActivity(this)
        }
    }

}