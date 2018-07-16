package rtviwe.com.retabelo.main.fragments.recommendations

import android.app.Application
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.recipe_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.model.database.recipe.RecipeDao
import rtviwe.com.retabelo.model.database.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.database.recipe.RecipeEntry

class RecommendationsAdapter(private val app: Application,
                             options: FirestorePagingOptions<RecipeEntry>)
    : FirestorePagingAdapter<RecipeEntry, RecommendationsAdapter.RecommendationsViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,
                parent, false)
        return RecommendationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int, model: RecipeEntry) {
        holder.bindTo(model)
    }

    inner class RecommendationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recipesDatabase: RecipeDatabase = RecipeDatabase.getInstance(app)
        private val recipesDao: RecipeDao = recipesDatabase.recipeDao()

        private val nameTextView = itemView.header_of_recipe
        private val markDownView = itemView.markdown_view

        fun bindTo(item: RecipeEntry) {
            nameTextView.text = item.name

            val previewText = item.body
                    .split(" ")
                    .joinToString(limit = 50, separator = " ")

            markDownView.loadMarkdown(previewText)

            RxView.clicks(itemView.favorite_button)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        RecipeEntry.changeFavorite(recipesDao, item)
                    }
        }
    }
}