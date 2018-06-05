package rtviwe.com.retabelo.main.fragments.foods

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.food_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsAdapter(private val context: Context)
    : PagedListAdapter<FoodEntry, FoodsAdapter.FoodViewHolder>(DIFF_CALLBACK) {

    private val clickSubject = PublishSubject.create<FoodEntry>()
    val clickEvent: Observable<FoodEntry> = clickSubject

    private var _foods: List<FoodEntry>? = null

    var foods
        set(value) {
            _foods = value
            notifyDataSetChanged()
        }
        get() = _foods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.food_list_item, parent, false)

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = getItem(position) as FoodEntry
        holder.bindTo(food)
    }

    override fun getItemCount() = if (foods != null) foods!!.size else 0

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodEntry>() {
            override fun areItemsTheSame(oldItem: FoodEntry, newItem: FoodEntry) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FoodEntry, newItem: FoodEntry) = oldItem == newItem
        }
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var picture: ImageView = itemView.image_view_photo_food
        private var name: TextView = itemView.text_view_name

        init {
            RxView.clicks(itemView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { clickSubject.onNext(foods!![adapterPosition]) }
        }

        fun bindTo(foodEntry: FoodEntry) {
            name.text = foodEntry.name

            val type = foodEntry.foodType
            picture.setImageResource(R.drawable.ic_receipt_black_24dp)
        }
    }
}