package rtviwe.com.retabelo.recommendations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.jakewharton.rxbinding2.widget.RxSearchView
import kotlinx.android.synthetic.main.recommendations_fragment.*
import kotlinx.coroutines.experimental.launch
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

        initAdapter()
        initRecyclerView()
        initSearchView()
    }

    override fun onStart() {
        super.onStart()
        recommendationsAdapter.startListening()
        recommendationsAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        recommendationsAdapter.stopListening()
    }

    override fun scrollToTop() {
        recommendationsLayoutManager.smoothScrollToPosition(recommendationsRecyclerView, RecyclerView.State(), 0)
    }

    private fun initAdapter() {
        val queryForRecipes = firebaseFirestore.collection("recipes")

        val config = PagedList.Config.Builder().apply {
            setPageSize(10)
            setPrefetchDistance(250)
            setEnablePlaceholders(true)
        }.build()

        val options = FirestorePagingOptions.Builder<RecipeEntry>().apply {
            setLifecycleOwner(this@RecommendationsFragment)
            setQuery(queryForRecipes, config, RecipeEntry::class.java)
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
        RxSearchView.queryTextChanges(search_view)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter {
                    !it.isEmpty()
                }
                .distinctUntilChanged()
                .subscribe({ searchText ->
                    recommendationsAdapter.clearAdapter()
                    firebaseFirestore.collection("recipes")
                            .whereArrayContains("name", searchText)
                            .get()
                            .addOnCompleteListener {
                                launch {
                                    recommendationsAdapter.addRecipes(it.result.documents)
                                }
                            }
                }, {
                    Log.e("ERROR", "$it")
                    it.printStackTrace()
                })
    }
}