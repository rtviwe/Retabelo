package rtviwe.com.retabelo.main.fragments.foods

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.food_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodEntry
import rtviwe.com.retabelo.main.DiffCallbackForFood

class FoodsAdapter(private val context: Context)
    : PagedListAdapter<FoodEntry, FoodsAdapter.FoodViewHolder>(DiffCallbackForFood) {

    private val clickSubject = PublishSubject.create<FoodEntry>()
    val clickEvent: Observable<FoodEntry> = clickSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.food_list_item, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindTo(getItem(position) as FoodEntry)
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var picture: ImageView = itemView.image_view_photo_food
        private var name: TextView = itemView.text_view_name

        lateinit var food: FoodEntry

        fun bindTo(foodEntry: FoodEntry?) {
            this.food = foodEntry!!

            name.text = food.name

            val type = food.foodType
            picture.setImageResource(R.drawable.ic_receipt_black_24dp)
        }
    }
}