package android.afebrerp.com.movies.presentation.view.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.koin.standalone.KoinComponent

abstract class BaseFragment : Fragment(), KoinComponent {

    protected var activityListener: BaseActivityFragmentInterface? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(getFragmentLayout(), container, false)

    @LayoutRes
    protected abstract fun getFragmentLayout(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activityListener = context as? BaseActivityFragmentInterface
    }

    override fun onDetach() {
        super.onDetach()
        activityListener = null
    }
}