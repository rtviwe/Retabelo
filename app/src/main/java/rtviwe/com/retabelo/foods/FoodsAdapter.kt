package rtviwe.com.retabelo.foods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.food_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.main.DiffCallback
import rtviwe.com.retabelo.model.food.FoodEntry

class FoodsAdapter
    : PagedListAdapter<FoodEntry, FoodsAdapter.FoodViewHolder>(DiffCallback<FoodEntry>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_list_item,
                parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item: FoodEntry? = getItem(position)
        item?.let {
            holder.bindTo(item)
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pictureImageView: ImageView = itemView.image_view_photo_food
        private var nameTextView: TextView = itemView.text_view_name

        lateinit var foodEntry: FoodEntry

        fun bindTo(food: FoodEntry) {
            foodEntry = food
            nameTextView.text = food.name
            pictureImageView.setImageResource(R.drawable.ic_receipt_black_24dp)
        }
    }
}