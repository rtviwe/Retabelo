package rtviwe.com.retabelo.main

import androidx.recyclerview.widget.DiffUtil
import rtviwe.com.retabelo.model.Entry

class DiffCallback<T : Entry> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}