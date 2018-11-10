package android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies

import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity
import android.afebrerp.com.movies.presentation.view.base.BaseActivityFragmentInterface
import android.afebrerp.com.movies.presentation.view.base.BaseFragment
import android.afebrerp.com.movies.presentation.view.popularmovies.PopularMoviesView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afebrerp.movies.android.R
import org.koin.android.viewmodel.ext.android.viewModel

class PopularMoviesFragment : BaseFragment() , PopularMoviesView {

    private var baseActivityFragmentInterface: BaseActivityFragmentInterface? = null
    private val caca: PopularMoviesViewModel by viewModel()

    override fun getFragmentLayout(): Int = R.layout.fragment_most_popular_movies

    private var mostPopularMoviesActivityFragmentInterface: BaseActivityFragmentInterface? = null
    private var searchAction: MenuItem? = null
    var isSearchExpanded: Boolean = false


    companion object {

        const val TAG = "PopularMoviesFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param breed Parameter 1.
         * @return A new instance of fragment PopularMoviesFragment.
         */
        fun newInstance(): PopularMoviesFragment = PopularMoviesFragment()
    }

    private val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
    )
    private var loading: Boolean = false

    private val onScrollListener = object : RecyclerView.OnScrollListener() {


        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            if (dy > 0) {
//                val visibleItemCount = linearLayoutManager.childCount
//                val totalItemCount = linearLayoutManager.itemCount
//                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
////                if (!presenter.isLastPage && !loading) {
////                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
////                        loading = true
////                        viewModel.getPopularMovies()
////                    }
////                }
//            } else if (linearLayoutManager.findLastVisibleItemPosition() == mostPopularMoviesRV.adapter.itemCount - 1
//                && !presenter.isLastPage
//            ) {
//                viewModel.getPopularMovies()
//                loading = true
//            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_most_popular_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setRefreshLayoutBehaviour()
//        setRecyclerView()
//        setEmptyView()
        setHasOptionsMenu(true)
//        viewModel.getPopularMovies() call on viewmodel itself

//        if (mostPopularMoviesRV.adapter == null)
//            mostPopularMoviesRV.adapter =
//                    MostPopularMovieListAdapter(viewModel.popularMoviesList.value as List<BaseListViewEntity>)
//
//        observe(viewModel.onDataReceived) {
//            showProgressBar(false)
//            showRecyclerView()
//            mostPopularMoviesRV.adapter.notifyDataSetChanged()
//        }
//
//        presenter.start()
    }

//
//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        mostPopularMoviesActivityFragmentInterface = context as? BaseActivityFragmentInterface
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        inflater?.inflate(R.menu.search_menu, menu)
//        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean = false
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (isSearchExpanded) {
//                    stopScroll()
//                    searchMovie(newText)
//                }
//                return true
//            }
//
//        })
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onPrepareOptionsMenu(menu: Menu?) {
//        searchAction = menu?.findItem(R.id.action_search)
//        searchAction?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
//            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
//                isSearchExpanded = true
//                animateSearchToolbar(1, true, true)
//                return true
//            }
//
//            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
//                isSearchExpanded = false
//                animateSearchToolbar(1, false, false)
//                stopScroll()
//                searchClosed()
//                getPopularMovies()
//                return true
//            }
//
//        })
//        super.onPrepareOptionsMenu(menu)
//    }
//
//    private fun stopScroll() {
////        mostPopularMoviesRV.stopScroll()
//    }
//
//
//    override fun showProgressBar(show: Boolean) {
//        swipeRefreshLayout?.let {
//            if (swipeRefreshLayout.isRefreshing) {
//                if (!show)
//                    swipeRefreshLayout.isRefreshing = false
//            } else progressBar?.visibility = if (show) View.VISIBLE else View.GONE
//        }
//    }
//
//    private fun setEmptyView() {
//        emptyView.fillViews(EmptyViewEnumeration.MOVIES_LIST_EMPTY_VIEW)
//    }
//
//    private fun setRecyclerView() {
//        mostPopularMoviesRV.layoutManager = linearLayoutManager
//        attachScrollListener()
//    }
//
//    private fun setRefreshLayoutBehaviour() =
//        swipeRefreshLayout.setOnRefreshListener {
//            loading = true
//            if (!presenter.isSearching) {
//                restartListAnimation()
//            }
//            viewModel.getPopularMovies(true)
//        }
//
//    override fun restartListAnimation() {
//        (mostPopularMoviesRV?.adapter as? MostPopularMovieListAdapter)?.restartLastPosition()
//    }
//
//    private fun attachScrollListener() =
//        mostPopularMoviesRV?.addOnScrollListener(onScrollListener)
//
//    override fun showRecyclerView() {
//        mostPopularMoviesRV?.visibility = View.VISIBLE
//    }
//
//    override fun hideRecyclerView() {
//        mostPopularMoviesRV?.visibility = View.GONE
//    }
//
//    override fun hideEmptyView() {
//        emptyView?.visibility = View.GONE
//    }
//
//    override fun showEmptyView() {
//        emptyView?.visibility = View.VISIBLE
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        presenter.destroy()
//        unattachScrollListener()
//    }
//
//    override fun setItems(moviesList: List<BaseListViewEntity>) {
//
////        mostPopularMoviesRV?.let {
////            if (mostPopularMoviesRV.adapter == null) mostPopularMoviesRV.adapter = MostPopularMovieListAdapter(moviesList)
////            mostPopularMoviesRV.adapter.notifyDataSetChanged()
////        }
//    }
//
//    override fun setLoadingState(state: Boolean) {
//        loading = state
//    }
//
//    private fun unattachScrollListener() =
//        mostPopularMoviesRV?.removeOnScrollListener(onScrollListener)
//
//    fun searchMovie(newText: String?) {
//        viewModel.searchMovieByText(newText, refreshList = true)
//    }
//
//    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
//
//        val toolbar = mostPopularMoviesActivityFragmentInterface?.getToolbar()
//        toolbar?.let {
//            toolbar.setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.white))
//
//            if (show) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    val createCircularReveal = createRevealAnimationReveal(toolbar, containsOverflow, numberOfMenuIcon)
//                    createCircularReveal.duration = 250
//                    createCircularReveal.start()
//                } else {
//                    searchRevealAnimationPreLollipop(toolbar)
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    val createCircularReveal = createCircularAnimationClose(toolbar, containsOverflow, numberOfMenuIcon)
//                    createCircularReveal.addListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationEnd(animation: Animator) {
//                            super.onAnimationEnd(animation)
//                            toolbar.setBackgroundColor(getThemeColor(context!!, R.attr.colorPrimary))
//                        }
//                    })
//                    createCircularReveal.start()
//                } else {
//                    searchCloseAnimationPreLollipop(toolbar)
//                }
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun createRevealAnimationReveal(
//        toolbar: Toolbar,
//        containsOverflow: Boolean,
//        numberOfMenuIcon: Int
//    ): Animator {
//        val width = toolbar.width -
//                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
//                else 0) -
//                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) *
//                numberOfMenuIcon / 2
//        return ViewAnimationUtils.createCircularReveal(
//            toolbar,
//            if (isRtl(resources)) toolbar.width - width
//            else width,
//            toolbar.height / 2,
//            0.0f,
//            width.toFloat()
//        )
//    }
//
//    private fun searchRevealAnimationPreLollipop(toolbar: Toolbar) {
//        val translateAnimation = TranslateAnimation(
//            0.0f,
//            0.0f,
//            -toolbar.height.toFloat(),
//            0.0f
//        )
//        translateAnimation.duration = 220
//        toolbar.clearAnimation()
//        toolbar.startAnimation(translateAnimation)
//    }
//
//    private fun searchCloseAnimationPreLollipop(toolbar: Toolbar) {
//        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
//        val translateAnimation = TranslateAnimation(
//            0.0f,
//            0.0f,
//            0.0f,
//            -toolbar.height.toFloat()
//        )
//        val animationSet = AnimationSet(true)
//        animationSet.addAnimation(alphaAnimation)
//        animationSet.addAnimation(translateAnimation)
//        animationSet.duration = 220
//        animationSet.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {}
//
//            override fun onAnimationEnd(animation: Animation) =
//                toolbar.setBackgroundColor(getThemeColor(context!!, R.attr.colorPrimary))
//
//            override fun onAnimationRepeat(animation: Animation) {}
//        })
//        toolbar.startAnimation(animationSet)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun createCircularAnimationClose(
//        toolbar: Toolbar,
//        containsOverflow: Boolean,
//        numberOfMenuIcon: Int
//    ): Animator {
//        val width = toolbar.width -
//                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
//                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
//        val createCircularReveal = ViewAnimationUtils.createCircularReveal(
//            toolbar,
//            if (isRtl(resources)) toolbar.width - width else width,
//            toolbar.height / 2,
//            width.toFloat(),
//            0.0f
//        )
//        createCircularReveal.duration = 250
//        return createCircularReveal
//    }
//
//    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private fun isRtl(resources: Resources): Boolean {
//        return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
//    }
//
//    private fun getThemeColor(context: Context, id: Int): Int {
//        val theme = context.theme
//        val a = theme.obtainStyledAttributes(intArrayOf(id))
//        val result = a.getColor(0, 0)
//        a.recycle()
//        return result
//    }
//
//    fun searchClosed() {
//        presenter.isSearching = false
//        presenter.cancelSearch()
//    }
//
//    fun getPopularMovies() {
//        presenter.start()
//    }

    override fun setItems(moviesList: List<BaseListViewEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgressBar(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoadingState(state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restartListAnimation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}