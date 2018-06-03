package rtviwe.com.retabelo.main.fragments.foods

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsAdapter(private val context: Context,
                   private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<FoodsAdapter.FavoriteViewHolder>() {

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
        holder.name.text = foods!![position].name
        holder.imageView.setImageResource(R.drawable.ic_receipt_black_24dp)
    }

    override fun getItemCount(): Int = if (foods != null) foods!!.size else 0

    interface ItemClickListener {

        fun onItemClickListener(itemId: Int)
    }

    inner class FavoriteViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var imageView: ImageView = itemView.findViewById(R.id.image_view_photo_food)
        var name: TextView = itemView.findViewById(R.id.text_view_name)

        override fun onClick(view: View?) {
            itemClickListener.onItemClickListener(foods!![adapterPosition].id)
        }
    }
}