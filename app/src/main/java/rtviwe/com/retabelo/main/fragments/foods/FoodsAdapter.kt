package rtviwe.com.retabelo.main.fragments.foods

import android.content.Context
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
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsAdapter(private val context: Context)
    : RecyclerView.Adapter<FoodsAdapter.FavoriteViewHolder>() {

    private val clickSubject = PublishSubject.create<FoodEntry>()
    val clickEvent: Observable<FoodEntry> = clickSubject

    private var _foods: List<FoodEntry>? = null

    var foods
        set(value) {
            _foods = value
            notifyDataSetChanged()
        }
        get() = _foods

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.food_list_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(foods!![position])
    }

    override fun getItemCount(): Int = if (foods != null) foods!!.size else 0

    inner class FavoriteViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        private var imageView: ImageView = itemView.findViewById(R.id.image_view_photo_food)
        private var name: TextView = itemView.findViewById(R.id.text_view_name)

        init {
            RxView.clicks(itemView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { clickSubject.onNext(foods!![adapterPosition]) }
        }

        fun bind(foodEntry: FoodEntry) {
            name.text = foodEntry.name
            imageView.setImageResource(R.drawable.ic_receipt_black_24dp)
        }
    }
}