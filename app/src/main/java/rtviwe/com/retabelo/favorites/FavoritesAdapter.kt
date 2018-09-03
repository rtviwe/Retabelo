package rtviwe.com.retabelo.favorites

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.favorites_fragment.view.*
import kotlinx.android.synthetic.main.recipe_item.view.*
import kotlinx.coroutines.experimental.launch
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.DiffCallback
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import rtviwe.com.retabelo.model.recipe.RecipePresenter

class FavoritesAdapter(private val fragment: FavoritesFragment)
    : PagedListAdapter<RecipeEntry, FavoritesAdapter.FavoriteViewHolder>(DiffCallback<RecipeEntry>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let { recipe ->
            holder.bindTo(recipe)
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recipesDao: RecipeDao = RecipeDatabase.getInstance(fragment.activity!!.application).recipeDao()

        private val nameTextView = itemView.name_of_recipe
        private val webView = itemView.web_view
        private val favoriteButton = itemView.favorite_button

        private var removedLastRecipe: RecipeEntry? = null

        fun bindTo(item: RecipeEntry) {
            nameTextView.text = item.name

            val previewText = item.body
                    .split(" ")
                    .joinToString(limit = 50, separator = " ")
                    .plus(" ...")

            RecipePresenter.loadWebView(webView, previewText)

            favoriteButton.isChecked = true

            itemView.setOnClickListener {
                RecipePresenter.openDetailActivity(fragment.context!!, item)
            }

            itemView.web_view.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    RecipePresenter.openDetailActivity(fragment.context!!, item)
                }
                true
            }

            itemView.favorite_button.setOnClickListener {
                RecipeEntry.changeFavorite(recipesDao, item)
                removedLastRecipe = item
                showSnackbar("${item.name} ${fragment.getString(R.string.undo_snackbar_favorite)}",
                        Snackbar.LENGTH_LONG)
            }
        }

        private fun showSnackbar(message: String, length: Int) {
            Snackbar.make(itemView.rootView.favorites_coordinator_layout, message, length).apply {
                setAction(R.string.undo_string) {
                    restoreFavoriteRecipe()
                }
                show()
            }
        }

        private fun restoreFavoriteRecipe() {
            removedLastRecipe?.let {
                launch {
                    RecipeEntry.changeFavorite(recipesDao, removedLastRecipe!!)
                }
            }
        }
    }
}
