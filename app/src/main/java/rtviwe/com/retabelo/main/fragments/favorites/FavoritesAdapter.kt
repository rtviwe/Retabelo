package rtviwe.com.retabelo.main.fragments.favorites

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.favorite_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.RecipeEntry
import rtviwe.com.retabelo.main.DiffCallback

class FavoritesAdapter(private val context: Context)
    : PagedListAdapter<RecipeEntry, FavoritesAdapter.FavoriteViewHolder>(DiffCallback<RecipeEntry>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.favorite_list_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.image_view_favorite_photo
        private val name: TextView = itemView.text_view_favorite_name
        private val trashView: ImageView = itemView.image_view_trash_photo

        lateinit var favoriteRecipe: RecipeEntry

        fun bindTo(favoriteRecipe: RecipeEntry?) {
            this.favoriteRecipe = favoriteRecipe!!
            imageView.setImageResource(R.drawable.ic_favorite_black_24dp)
            name.text = favoriteRecipe.name
            trashView.setImageResource(R.drawable.ic_delete_black_24dp)

            RxView.clicks(imageView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.v("ImageView", favoriteRecipe.toString())
                    }

            RxView.clicks(trashView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.v("TrashView", favoriteRecipe.toString())
                    }
        }
    }
}