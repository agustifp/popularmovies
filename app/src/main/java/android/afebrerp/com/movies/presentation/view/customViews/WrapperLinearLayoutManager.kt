package android.afebrerp.com.movies.presentation.view.customViews

import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapperLinearLayoutManager : LinearLayoutManager {
    constructor(context: FragmentActivity) : super(context)

    constructor(context: FragmentActivity?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

    constructor(context: FragmentActivity, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("probe", "meet a IOOBE in RecyclerView")
        }
    }

}
