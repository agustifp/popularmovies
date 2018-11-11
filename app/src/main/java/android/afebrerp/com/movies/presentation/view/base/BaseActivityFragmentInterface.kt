package android.afebrerp.com.movies.presentation.view.base

import androidx.appcompat.widget.Toolbar


interface BaseActivityFragmentInterface {
    fun getToolbar(): Toolbar
    fun isInternetReachable(): Boolean
    fun showMessage(message: String)
}