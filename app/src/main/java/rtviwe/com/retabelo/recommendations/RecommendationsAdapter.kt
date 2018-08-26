package rtviwe.com.retabelo.recommendations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.recipe_item.view.*
import kotlinx.android.synthetic.main.recommendations_fragment.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import rtviwe.com.retabelo.model.recipe.RecipePresenter


class RecommendationsAdapter(private val fragment: Fragment,
                             options: FirestorePagingOptions<RecipeEntry>)
    : FirestorePagingAdapter<RecipeEntry, RecommendationsAdapter.RecommendationsViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)

        return RecommendationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int, model: RecipeEntry) {
        holder.bindTo(model)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING_INITIAL -> fragment.progress_bar.visibility = View.VISIBLE
            LoadingState.LOADING_MORE -> fragment.progress_bar.visibility = View.GONE
            LoadingState.LOADED -> fragment.progress_bar.visibility = View.GONE
            LoadingState.ERROR -> fragment.progress_bar.visibility = View.VISIBLE
            else -> fragment.progress_bar.visibility = View.GONE
        }
    }

    fun clearAdapter() {

    }

    fun addRecipes(recipes: List<DocumentSnapshot>) {

    }

    inner class RecommendationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recipesDao: RecipeDao = RecipeDatabase.getInstance(fragment.activity!!.application).recipeDao()

        private val nameTextView = itemView.name_of_recipe
        private val webView = itemView.web_view
        private val favoriteButton = itemView.favorite_button

        fun bindTo(item: RecipeEntry) {
            nameTextView.text = item.name

            val previewText = item.body
                    .split(" ")
                    .joinToString(limit = 50, separator = " ")
                    .plus(" ...")

            RecipePresenter.loadWebView(webView, previewText)
            RecipeEntry.setIsFavorite(recipesDao, item.name, favoriteButton)

            itemView.setOnClickListener {
                RecipePresenter.openDetailActivity(fragment.context!!, item)
            }

            itemView.favorite_button.setOnClickListener {
                RecipeEntry.changeFavorite(recipesDao, item)
            }
        }
    }
}