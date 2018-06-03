package rtviwe.com.retabelo.main.fragments.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.favorite.FavoriteEntry

class FavoritesAdapter(private val context: Context,
                    private val favoriteClickListener: FavoriteClickListener)
    : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private var _favorites: List<FavoriteEntry>? = null

    var favorites
        set(value) {
            _favorites = value
            notifyDataSetChanged()
        }
        get() = _favorites

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.favorite_list_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.name.text = favorites!![position].name
        holder.imageView.setImageResource(R.drawable.ic_favorite_black_24dp)
        holder.trashView.setImageResource(R.drawable.ic_delete_black_24dp)
    }

    override fun getItemCount(): Int = if (favorites != null) favorites!!.size else 0

    interface FavoriteClickListener {

        fun onFavoriteClickListener(itemId: Int)
        fun onTrashClickListener(itemId: Int)
    }

    inner class FavoriteViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var imageView: ImageView = itemView.findViewById(R.id.image_view_favorite_photo)
        var name: TextView = itemView.findViewById(R.id.text_view_favorite_name)
        var trashView: ImageView = itemView.findViewById(R.id.image_view_trash_photo)

        override fun onClick(view: View?) {
            favoriteClickListener.onFavoriteClickListener(favorites!![adapterPosition].id)
            favoriteClickListener.onTrashClickListener(favorites!![adapterPosition].id)
        }
    }
}