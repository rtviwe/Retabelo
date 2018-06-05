package rtviwe.com.retabelo.main

import android.support.v7.util.DiffUtil
import rtviwe.com.retabelo.database.Entry
import rtviwe.com.retabelo.database.recipe.RecipeEntry

/**
* Created by festelo on 06.06.2018.
*/

class DiffCallback<T> : DiffUtil.ItemCallback<T>() where T : Entry {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}