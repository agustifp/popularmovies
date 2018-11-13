package android.afebrerp.com.movies.presentation.navigation

import android.afebrerp.com.movies.presentation.view.popularmovies.MostPopularMoviesActivity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.afebrerp.movies.android.R


object Navigator {

    private const val FLAGS_CLEAR_STACK = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    enum class ActivityTransitionEnum(val pair: Pair<Int, Int>) {
        FADE_IN(android.R.anim.fade_in to android.R.anim.fade_out),
        RIGHT(R.anim.slide_in_right to R.anim.slide_out_left),
        LEFT(R.anim.slide_in_left to R.anim.slide_out_right),
        NONE(0 to 0)
    }

    fun openMainActivity(context: Context) =
            openActivity(context, MostPopularMoviesActivity::class.java, true, ActivityTransitionEnum.FADE_IN)

    private fun setAnimation(activity: AppCompatActivity, animation: ActivityTransitionEnum) {
        if (animation != ActivityTransitionEnum.NONE)
            activity.overridePendingTransition(animation.pair.first, animation.pair.second)
    }

    fun openUrl(context: Context, url: String) =
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

    private fun <T> createIntent(context: Context, activity: Class<T>, clearStack: Boolean = false) =
            Intent(context, activity).apply { if (clearStack) flags = FLAGS_CLEAR_STACK }

    private fun <T> openActivity(context: Context, activity: Class<T>, clearStack: Boolean = false,
                                 animation: ActivityTransitionEnum = ActivityTransitionEnum.NONE
    ) =
            (context as? AppCompatActivity)?.let {
                it.startActivity(createIntent(it, activity, clearStack))
                setAnimation(it, animation)
            }
}
