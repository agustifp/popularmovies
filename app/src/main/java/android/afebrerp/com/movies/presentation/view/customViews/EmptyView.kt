package android.afebrerp.com.movies.presentation.view.customViews

import android.afebrerp.com.movies.presentation.view.enumerations.EmptyViewEnumeration
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.afebrerp.movies.android.R
import kotlinx.android.synthetic.main.generic_empty_view.view.*

class EmptyView : FrameLayout {
    private var imageView: ImageView? = null
    private var titleTv: TextView? = null
    private var textTv: TextView? = null
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.generic_empty_view, this, true)
        imageView = view.genericEmptyViewIv
        titleTv = view.genericEmptyViewTitleTv
        textTv = view.genericEmptyViewTextTv
    }
    fun fillViews(emptyViewEnumeration: EmptyViewEnumeration) {
        imageView!!.setImageDrawable(ContextCompat.getDrawable(context, emptyViewEnumeration.imageId))
        titleTv!!.text = context.getString(emptyViewEnumeration.title)
        textTv!!.text = context.getString(emptyViewEnumeration.subtitle)
    }
}