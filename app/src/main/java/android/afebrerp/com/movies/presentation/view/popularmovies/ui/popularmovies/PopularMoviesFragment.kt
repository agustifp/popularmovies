package android.afebrerp.com.movies.presentation.view.popularmovies.ui.popularmovies

import android.afebrerp.com.movies.presentation.adapters.PopularMovieListAdapter
import android.afebrerp.com.movies.presentation.entities.MovieViewEntity
import android.afebrerp.com.movies.presentation.entities.base.BaseListViewEntity
import android.afebrerp.com.movies.presentation.extensions.observe
import android.afebrerp.com.movies.presentation.glideModule.GlideApp
import android.afebrerp.com.movies.presentation.view.base.BaseActivityFragmentInterface
import android.afebrerp.com.movies.presentation.view.base.BaseFragment
import android.afebrerp.com.movies.presentation.view.customViews.WrapperLinearLayoutManager
import android.afebrerp.com.movies.presentation.view.enumerations.EmptyViewEnumeration
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.SearchManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afebrerp.movies.android.R
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import kotlinx.android.synthetic.main.fragment_most_popular_movies.emptyView
import kotlinx.android.synthetic.main.fragment_most_popular_movies.mostPopularMoviesRV
import kotlinx.android.synthetic.main.fragment_most_popular_movies.progressBar
import kotlinx.android.synthetic.main.fragment_most_popular_movies.swipeRefreshLayout
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.Collections

class PopularMoviesFragment : BaseFragment() {

    private val viewModel: PopularMoviesViewModel by viewModel()
    private var baseActivityFragmentInterface: BaseActivityFragmentInterface? = null
    private lateinit var movieListAdapter: PopularMovieListAdapter
    private var searchAction: MenuItem? = null
    private var isSearchExpanded: Boolean = false
    private var loading: Boolean = false

    companion object {
        const val TAG = "PopularMoviesFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PopularMoviesFragment.
         */
        fun newInstance(): PopularMoviesFragment = PopularMoviesFragment()
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_most_popular_movies

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setRefreshLayoutBehaviour()
        setRecyclerView()
        setEmptyView()
        setHasOptionsMenu(true)
        setObservers()
    }

    private fun setObservers() {
        observe(viewModel.popularMoviesList) {
            it?.let { itemsSecure ->
                loadItems(itemsSecure)
            }
            showProgressBar(false)
            showRecyclerView()
        }

        observe(viewModel.onErrorReceived) {
            it?.let { it1 -> baseActivityFragmentInterface?.showMessage(it1) }
            showProgressBar(false)
        }
    }

    private fun setAdapter() {
        if (mostPopularMoviesRV.adapter == null) {

            movieListAdapter = PopularMovieListAdapter(listOf())
            mostPopularMoviesRV.adapter = movieListAdapter

            val preLoader = RecyclerViewPreloader(
                    GlideApp.with(this),
                    MyPreloadModelProvider(),
                    FixedPreloadSizeProvider<String>(1024, 768),
                    10)
            mostPopularMoviesRV.addOnScrollListener(preLoader)
        }
    }

    inner class MyPreloadModelProvider : ListPreloader.PreloadModelProvider<String> {

        override fun getPreloadItems(position: Int): MutableList<String> {
            var uri = ""
            when (val item = movieListAdapter.movieList[position]) {
                is MovieViewEntity -> uri = (item).imageUri
            }
            return if (TextUtils.isEmpty(uri)) {
                Collections.emptyList()
            } else {
                Collections.singletonList(uri)
            }
        }

        override fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? =
                GlideApp.with(activity!!.applicationContext).load(url)
    }

    private val linearLayoutManager: LinearLayoutManager = WrapperLinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false)

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

            var loadFromBottom = false
            mostPopularMoviesRV.adapter?.itemCount?.let { itemCount ->
                loadFromBottom = !viewModel.isLastPage && linearLayoutManager.findLastVisibleItemPosition() == (itemCount - 1)
            }

            if (dy > 0) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                if (!viewModel.isLastPage) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount - 5) {
                        viewModel.loadMoreMovies()
                    }
                }
            } else if (loadFromBottom) {
                viewModel.loadMoreMovies()
            }
        }
    }

    private fun loadItems(list: List<BaseListViewEntity>) {

//        urlArrayList.addAll(populateUrls(list))

        if (movieListAdapter.movieList.isEmpty()) {
            movieListAdapter.movieList = list
        }
        movieListAdapter.notifyDataSetChanged()
        showEmptyView(movieListAdapter.movieList.isEmpty())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        baseActivityFragmentInterface = context as? BaseActivityFragmentInterface
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                if (isSearchExpanded) {
                    stopScroll()
                    if (newText != "") {
                        searchMovie(newText)
                    }
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        searchAction = menu?.findItem(R.id.action_search)
        searchAction?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                isSearchExpanded = true
                mostPopularMoviesRV.recycledViewPool.clear()
                animateSearchToolbar(1, true, true)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                isSearchExpanded = false
                animateSearchToolbar(1, false, false)
                stopScroll()
                linearLayoutManager.removeAllViews()
                mostPopularMoviesRV.recycledViewPool.clear()
                viewModel.isSearching = false
                viewModel.start()
                return true
            }

        })
        super.onPrepareOptionsMenu(menu)
    }

    private fun stopScroll() {
        mostPopularMoviesRV.stopScroll()
    }

    private fun showProgressBar(show: Boolean) {
        swipeRefreshLayout?.let {
            if (swipeRefreshLayout.isRefreshing) {
                if (!show)
                    swipeRefreshLayout.isRefreshing = false
            } else progressBar?.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun setEmptyView() {
        emptyView.fillViews(EmptyViewEnumeration.MOVIES_LIST_EMPTY_VIEW)
    }

    private fun setRecyclerView() {
        mostPopularMoviesRV.layoutManager = linearLayoutManager
        attachScrollListener()
    }

    private fun setRefreshLayoutBehaviour() =
            swipeRefreshLayout.setOnRefreshListener {
                loading = true
                if (!viewModel.isSearching) {
                    restartListAnimation()
                    viewModel.start()
                } else {
                    viewModel.loadMoreMovies()
                }
            }

    private fun restartListAnimation() {
        (mostPopularMoviesRV?.adapter as? PopularMovieListAdapter)?.restartLastPosition()
    }

    private fun attachScrollListener() =
            mostPopularMoviesRV?.addOnScrollListener(onScrollListener)

    private fun showRecyclerView() {
        mostPopularMoviesRV?.visibility = View.VISIBLE
    }

    private fun showEmptyView(visibility: Boolean) {
        emptyView?.visibility = when {
            visibility -> View.VISIBLE
            else -> View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unAttachScrollListener()
    }

    private fun unAttachScrollListener() =
            mostPopularMoviesRV?.removeOnScrollListener(onScrollListener)

    fun searchMovie(newText: String) {
        viewModel.searchMovieByText(newText)
    }

    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
        baseActivityFragmentInterface?.getToolbar()?.let { toolbar ->
            context?.let { context ->
                toolbar.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))

                if (show) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val createCircularReveal = createRevealAnimationReveal(toolbar, containsOverflow, numberOfMenuIcon)
                        createCircularReveal.duration = 250
                        createCircularReveal.start()
                    } else {
                        searchRevealAnimationPreLollipop(toolbar)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val createCircularReveal = createCircularAnimationClose(toolbar, containsOverflow, numberOfMenuIcon)
                        createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                toolbar.setBackgroundColor(getThemeColor(context, R.attr.colorPrimary))
                            }
                        })
                        createCircularReveal.start()
                    } else {
                        searchCloseAnimationPreLollipop(toolbar)
                    }
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createRevealAnimationReveal(
            toolbar: Toolbar,
            containsOverflow: Boolean,
            numberOfMenuIcon: Int
    ): Animator {
        val width = toolbar.width -
                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
                else 0) -
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) *
                numberOfMenuIcon / 2
        return ViewAnimationUtils.createCircularReveal(
                toolbar,
                if (isRtl(resources)) toolbar.width - width
                else width,
                toolbar.height / 2,
                0.0f,
                width.toFloat()
        )
    }

    private fun searchRevealAnimationPreLollipop(toolbar: Toolbar) {
        val translateAnimation = TranslateAnimation(
                0.0f,
                0.0f,
                -toolbar.height.toFloat(),
                0.0f
        )
        translateAnimation.duration = 220
        toolbar.clearAnimation()
        toolbar.startAnimation(translateAnimation)
    }

    private fun searchCloseAnimationPreLollipop(toolbar: Toolbar) {
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        val translateAnimation = TranslateAnimation(
                0.0f,
                0.0f,
                0.0f,
                -toolbar.height.toFloat()
        )
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(translateAnimation)
        animationSet.duration = 220
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                context?.let { context -> toolbar.setBackgroundColor(getThemeColor(context, R.attr.colorPrimary)) }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        toolbar.startAnimation(animationSet)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createCircularAnimationClose(
            toolbar: Toolbar,
            containsOverflow: Boolean,
            numberOfMenuIcon: Int
    ): Animator {
        val width = toolbar.width -
                (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) else 0) -
                resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon / 2
        val createCircularReveal = ViewAnimationUtils.createCircularReveal(
                toolbar,
                if (isRtl(resources)) toolbar.width - width else width,
                toolbar.height / 2,
                width.toFloat(),
                0.0f
        )
        createCircularReveal.duration = 250
        return createCircularReveal
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun isRtl(resources: Resources): Boolean {
        return resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }

    private fun getThemeColor(context: Context, id: Int): Int {
        val theme = context.theme
        val a = theme.obtainStyledAttributes(intArrayOf(id))
        val result = a.getColor(0, 0)
        a.recycle()
        return result
    }

}