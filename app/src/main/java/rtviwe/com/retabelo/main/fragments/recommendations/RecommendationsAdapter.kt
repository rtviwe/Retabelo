package rtviwe.com.retabelo.main.fragments.recommendations

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.recommendation_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.RecipeEntry
import rtviwe.com.retabelo.main.DiffCallback

class RecommendationsAdapter(private val context: Context)
    : PagedListAdapter<RecipeEntry, RecommendationsAdapter.RecipeViewHolder>(DiffCallback<RecipeEntry>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.food_list_item, parent, false)

        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindTo(getItem(position)!!)
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var photoImageView: ImageView = itemView.image_view_recommended_dish
        private var nameTextView: TextView = itemView.name_of_recommended_dish
        //private var preText: TextView = ...

        fun bindTo(recipe: RecipeEntry) {
            photoImageView.setImageResource(R.drawable.ic_receipt_black_24dp)
            nameTextView.text = recipe.name
        }
    }
}