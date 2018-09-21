package rtviwe.com.retabelo.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.jakewharton.rxbinding2.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.MainBaseFragment
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import java.util.concurrent.TimeUnit


class RecommendationsFragment : MainBaseFragment() {

    override val layoutId = R.layout.recommendations_fragment

    private lateinit var firebaseFirestore: FirebaseFirestore

    private lateinit var recipesDao: RecipeDao
    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private lateinit var recommendationsRecyclerView: RecyclerView
    private lateinit var recommendationsLayoutManager: LinearLayoutManager

    var clickedItemPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder().apply {
            setTimestampsInSnapshotsEnabled(true)
        }.build()

        recipesDao = RecipeDatabase.getInstance(context!!).recipeDao()

        initAdapter(firebaseFirestore.collection("recipes").limit(10))
        initRecyclerView()
        initSearchView()
        initSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        recommendationsAdapter.notifyItemChanged(clickedItemPosition)
    }

    override fun scrollToTop() {
        recommendationsLayoutManager.smoothScrollToPosition(recommendationsRecyclerView, RecyclerView.State(), 0)
    }

    private fun initAdapter(query: Query) {
        val config = PagedList.Config.Builder().apply {
            setPageSize(10)
            setPrefetchDistance(480)
            setEnablePlaceholders(false)
        }.build()

        val options = FirestorePagingOptions.Builder<RecipeEntry>().apply {
            setLifecycleOwner(this@RecommendationsFragment)
            setQuery(query, Source.DEFAULT, config, RecipeEntry::class.java)
        }.build()

        recommendationsAdapter = RecommendationsAdapter(this, options)
    }

    private fun initRecyclerView() {
        recommendationsLayoutManager = LinearLayoutManager(context)

        recommendationsRecyclerView = recycler_view_recommendations.apply {
            layoutManager = recommendationsLayoutManager
            adapter = recommendationsAdapter
        }
    }

    private fun initSearchView() {
        var isSearchTextEmpty = true

        RxSearchView.queryTextChanges(search_view)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter {
                    isSearchTextEmpty = it.isEmpty()
                    !isSearchTextEmpty
                }
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchText ->
                    clearAdapter()
                    addRecipes(searchText.toString())
                }, {
                    it.printStackTrace()
                })

        search_view.setOnCloseListener {
            if (!isSearchTextEmpty) {
                refreshAdapter()
                scrollToTop()
            }
            false
        }
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh.setOnRefreshListener {
            refreshAdapter()
        }
    }

    private fun refreshAdapter() {
        initAdapter(firebaseFirestore.collection("recipes"))
        recommendationsAdapter.isLoadingFromSwipe = true
        initRecyclerView()
    }

    private fun clearAdapter() {
        initAdapter(firebaseFirestore.collection("empty"))
        recommendationsAdapter.isLoadingFromSwipe = false
        initRecyclerView()
    }

    private fun addRecipes(searchText: String) {
        initAdapter(firebaseFirestore.collection("recipes").whereEqualTo("name", searchText))
        recommendationsAdapter.isLoadingFromSwipe = false
        initRecyclerView()
    }
}