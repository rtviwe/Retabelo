package rtviwe.com.retabelo.main

import android.support.v7.util.DiffUtil
import rtviwe.com.retabelo.database.food.FoodEntry

object DiffCallbackForFood : DiffUtil.ItemCallback<FoodEntry>() {

    override fun areItemsTheSame(oldItem: FoodEntry, newItem: FoodEntry) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FoodEntry, newItem: FoodEntry) = oldItem == newItem
}

