package rtviwe.com.retabelo.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.recipe.RecipeEntry


class RecommendationsFragment : Fragment() {

    val layoutId = R.layout.recommendations_fragment

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recommendationsAdapter: RecommendationsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        val queryForRecipes = firebaseFirestore.collection("recipes")

        val config = PagedList.Config.Builder().apply {
            setPageSize(20)
            setEnablePlaceholders(true)
        }.build()

        val options = FirestorePagingOptions.Builder<RecipeEntry>().apply {
            setLifecycleOwner(this@RecommendationsFragment)
            setQuery(queryForRecipes, config, RecipeEntry::class.java)
        }.build()

        recommendationsAdapter = RecommendationsAdapter(this, options)
    }

    private fun initRecyclerView() {
        recycler_view_recommendations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recommendationsAdapter
        }
    }
}