package android.afebrerp.com.movies.presentation.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateFromLayout(@LayoutRes layout: Int): View =
        LayoutInflater.from(context).inflate(layout, this, false)
