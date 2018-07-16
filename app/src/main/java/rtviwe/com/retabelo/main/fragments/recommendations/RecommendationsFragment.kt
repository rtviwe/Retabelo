package rtviwe.com.retabelo.main.fragments.recommendations

import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.Recipe
import rtviwe.com.retabelo.main.fragments.BaseFragment


class RecommendationsFragment : BaseFragment() {

    override val layoutId = R.layout.recommendations_fragment

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebasePhotosReference: StorageReference

    private lateinit var recommendationsAdapter: RecommendationsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        firebasePhotosReference = firebaseStorage.reference.child("photos")

        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        val queryForRecipes = firebaseFirestore.collection("recipes")

        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setPrefetchDistance(10)
            setPageSize(20)
        }.build()

        val options = FirestorePagingOptions.Builder<Recipe>().apply {
            setLifecycleOwner(this@RecommendationsFragment)
            setQuery(queryForRecipes, config, Recipe::class.java)
        }.build()

        recommendationsAdapter = RecommendationsAdapter(options)
    }

    private fun initRecyclerView() {
        recycler_view_recommendations.apply {
            addItemDecoration(DividerItemDecoration(activity?.applicationContext,
                    DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = recommendationsAdapter
        }
    }
}