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
import rtviwe.com.retabelo.model.food.FoodType

class FoodsAdapter
    : PagedListAdapter<FoodEntry, FoodsAdapter.FoodViewHolder>(DiffCallback<FoodEntry>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_list_item,
                parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        getItem(position)?.let { food ->
            holder.bindTo(food)
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pictureImageView: ImageView = itemView.image_view_photo_food
        private var nameTextView: TextView = itemView.text_view_name

        lateinit var foodEntry: FoodEntry

        fun bindTo(food: FoodEntry) {
            foodEntry = food
            nameTextView.text = food.name

            pictureImageView.setImageResource(when (food.foodType) {
                FoodType.LIQUID -> R.drawable.liquid
                FoodType.BREAD -> R.drawable.bread
                FoodType.ALCOHOL -> R.drawable.alcohol
                FoodType.FRUIT -> R.drawable.fruit
                FoodType.VEGETABLE -> R.drawable.vegetable
                FoodType.GROCERY -> R.drawable.grocery
                FoodType.FISH -> R.drawable.fish
                FoodType.MEAT -> R.drawable.meat
                FoodType.SWEET -> R.drawable.sweet
                FoodType.CHEESE -> R.drawable.cheese
                FoodType.CEREALS -> R.drawable.cereals
                FoodType.OTHER -> R.drawable.other
            })
        }
    }
}