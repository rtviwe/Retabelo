package rtviwe.com.retabelo.main.fragments.recommendations

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.RecipeEntry
import rtviwe.com.retabelo.main.fragments.BaseFragment


class RecommendationsFragment : BaseFragment() {

    override val layoutId = R.layout.recommendations_fragment

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recommendationsAdapter: RecommendationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        val queryForRecipes = firebaseFirestore.collection("recipes")

        val config = PagedList.Config.Builder().apply {
            setPrefetchDistance(10)
            setPageSize(20)
        }.build()

        val options = FirestorePagingOptions.Builder<RecipeEntry>().apply {
            setLifecycleOwner(this@RecommendationsFragment)
            setQuery(queryForRecipes, config, RecipeEntry::class.java)
        }.build()

        recommendationsAdapter = RecommendationsAdapter(activity!!.application, options)
    }

    private fun initRecyclerView() {
        recycler_view_recommendations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recommendationsAdapter
        }
    }
}