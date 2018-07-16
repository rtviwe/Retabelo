package rtviwe.com.retabelo.main

import android.support.v7.util.DiffUtil
import rtviwe.com.retabelo.model.Entry

/**
* Created by festelo on 06.06.2018.
*/

class DiffCallback<T : Entry> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}