package rtviwe.com.retabelo.main.fragments.favorites

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
import kotlinx.android.synthetic.main.favorite_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.RecipeEntry
import rtviwe.com.retabelo.main.DiffCallback

class FavoritesAdapter(private val context: Context)
    : PagedListAdapter<RecipeEntry, FavoritesAdapter.FavoriteViewHolder>(DiffCallback<RecipeEntry>()) {

    private val clickSubject = PublishSubject.create<RecipeEntry>()
    val clickEvent: Observable<RecipeEntry> = clickSubject

    private val clickSubjectOnTrash = PublishSubject.create<RecipeEntry>()
    val clickEventOnTrash: Observable<RecipeEntry> = clickSubjectOnTrash

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

        fun bindTo(favoriteEntry: RecipeEntry?) {
            this.favoriteRecipe = favoriteEntry!!

            imageView.setImageResource(R.drawable.ic_favorite_black_24dp)
            name.text = favoriteEntry.name
            trashView.setImageResource(R.drawable.ic_delete_black_24dp)
        }
    }
}