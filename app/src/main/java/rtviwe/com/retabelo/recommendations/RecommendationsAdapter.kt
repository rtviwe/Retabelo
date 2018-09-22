package rtviwe.com.retabelo.recommendations

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import kotlinx.android.synthetic.main.recipe_item.view.*
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.details.RecipeDetail
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry


class RecommendationsAdapter(private val fragment: RecommendationsFragment,
                             options: FirestorePagingOptions<RecipeEntry>)
    : FirestorePagingAdapter<RecipeEntry, RecommendationsAdapter.RecommendationsViewHolder>(options) {

    var isLoadingFromSwipe = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)

        return RecommendationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int, model: RecipeEntry) {
        holder.bindTo(model)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> {
                if (isLoadingFromSwipe) fragment.swipe_refresh.isRefreshing = true
                else fragment.progress_bar.visibility = View.VISIBLE
            }
            LoadingState.LOADING_MORE -> fragment.progress_bar.visibility = View.GONE
            LoadingState.LOADED -> {
                fragment.progress_bar.visibility = View.GONE
                fragment.swipe_refresh.isRefreshing = false
            }
            LoadingState.ERROR -> fragment.progress_bar.visibility = View.VISIBLE
            else -> fragment.progress_bar.visibility = View.GONE
        }
    }

    inner class RecommendationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recipesDao: RecipeDao = RecipeDatabase.getInstance(fragment.activity!!.application).recipeDao()

        private val nameTextView = itemView.name_of_recipe
        private val webView = itemView.web_view
        private val favoriteButton = itemView.favorite_button

        fun bindTo(item: RecipeEntry) {
            nameTextView.text = if (item.name.count() <= 20)
                item.name
            else
                item.name.take(20).plus("...")

            val previewText = item.body
                    .split(" ")
                    .joinToString(limit = 50, separator = " ")
                    .plus(" ...")

            RecipeEntry.loadWebView(webView, previewText)
            RecipeEntry.setIconAndItemFavorite(recipesDao, item.name, favoriteButton)

            itemView.setOnClickListener {
                fragment.clickedItemPosition = adapterPosition
                openDetailsFromRecommendations(item)
            }

            itemView.web_view.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    fragment.clickedItemPosition = adapterPosition
                    openDetailsFromRecommendations(item)
                }
                true
            }

            itemView.favorite_button.setOnClickListener {
                RecipeEntry.changeFavorite(recipesDao, item)
            }
        }

        private fun openDetailsFromRecommendations(item: RecipeEntry) {
            val bundle = bundleOf(
                    RecipeDetail.EXTRA_NAME to item.name,
                    RecipeDetail.EXTRA_BODY to item.body
            )
            val navController = Navigation.findNavController(fragment.activity!!, R.id.nav_host_fragment)
            navController.navigate(R.id.action_recommendationsFragment_to_recipeDetail, bundle)
        }
    }
}