package rtviwe.com.retabelo.foods

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
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
        holder.bindTo(getItem(position)!!)
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pictureImageView: ImageView = itemView.image_view_photo_food
        private var nameTextView: TextView = itemView.text_view_name

        lateinit var food: FoodEntry

        fun bindTo(food: FoodEntry) {
            this.food = food
            nameTextView.text = food.name
            pictureImageView.setImageResource(R.drawable.ic_receipt_black_24dp)

            RxView.clicks(itemView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.v("FoodViewHolder", food.toString())
                    }
        }
    }
}