package rtviwe.com.retabelo.main

import android.support.v7.util.DiffUtil
import rtviwe.com.retabelo.database.recipe.RecipeEntry

object DiffCallbackForFavorite : DiffUtil.ItemCallback<RecipeEntry>() {

    override fun areItemsTheSame(oldItem: RecipeEntry, newItem: RecipeEntry) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RecipeEntry, newItem: RecipeEntry) = oldItem == newItem
}