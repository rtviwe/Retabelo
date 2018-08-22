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
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.MainBaseFragment
import rtviwe.com.retabelo.model.recipe.RecipeEntry


class RecommendationsFragment : MainBaseFragment() {

    override val layoutId = R.layout.recommendations_fragment

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var recommendationsAdapter: RecommendationsAdapter
    private lateinit var recommendationsRecyclerView: RecyclerView
    private lateinit var recommendationsLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
    }

    override fun scrollToTop() {
        recommendationsLayoutManager.smoothScrollToPosition(recycler_view_favorites, RecyclerView.State(), 0)
    }

    private fun initAdapter() {
        val queryForRecipes = firebaseFirestore.collection("recipes")

        val config = PagedList.Config.Builder().apply {
            setPageSize(10)
            setPrefetchDistance(250)
            setInitialLoadSizeHint(10)
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
}