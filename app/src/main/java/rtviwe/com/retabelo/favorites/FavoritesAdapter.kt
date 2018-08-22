package rtviwe.com.retabelo.favorites

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.favorites_fragment.view.*
import kotlinx.android.synthetic.main.recipe_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.DiffCallback
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry
import rtviwe.com.retabelo.model.recipe.RecipePresenter

class FavoritesAdapter(private val app: Application)
    : PagedListAdapter<RecipeEntry, FavoritesAdapter.FavoriteViewHolder>(DiffCallback<RecipeEntry>()) {

    private val disposableDatabase = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item: RecipeEntry? = getItem(position)
        if (item != null) {
            holder.bindTo(item)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposableDatabase.clear()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recipesDao: RecipeDao = RecipeDatabase.getInstance(app).recipeDao()

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
                RecipePresenter.openDetailActivity(app.applicationContext, item)
            }

            itemView.favorite_button.setOnClickListener {
                RecipeEntry.changeFavorite(recipesDao, item)
                removedLastRecipe = item
                showSnackbar("${item.name} ${app.getString(R.string.undo_snackbar_favorite)}",
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
            if (removedLastRecipe != null) {
                disposableDatabase.add(Completable.fromAction {
                            RecipeEntry.changeFavorite(recipesDao, removedLastRecipe!!)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                )
            }
        }
    }
}