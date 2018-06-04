package rtviwe.com.retabelo.main.fragments.favorites

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
import rtviwe.com.retabelo.database.recipe.RecipeEntry

class FavoritesAdapter(private val context: Context)
    : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    private val clickSubject = PublishSubject.create<RecipeEntry>()
    val clickEvent: Observable<RecipeEntry> = clickSubject

    private val clickSubjectOnTrash = PublishSubject.create<RecipeEntry>()
    val clickEventOnTrash: Observable<RecipeEntry> = clickSubjectOnTrash

    private var _favorites: List<RecipeEntry>? = null

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
        holder.bind(favorites!![position])
    }

    override fun getItemCount(): Int = if (favorites != null) favorites!!.size else 0

    inner class FavoriteViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.image_view_favorite_photo)
        var name: TextView = itemView.findViewById(R.id.text_view_favorite_name)
        var trashView: ImageView = itemView.findViewById(R.id.image_view_trash_photo)

        init {
            RxView.clicks(itemView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { clickSubject.onNext(favorites!![adapterPosition]) }

            RxView.clicks(trashView)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { clickSubjectOnTrash.onNext(favorites!![adapterPosition]) }
        }

        fun bind(favoriteEntry: RecipeEntry) {
            imageView.setImageResource(R.drawable.ic_favorite_black_24dp)
            name.text = favoriteEntry.name
            trashView.setImageResource(R.drawable.ic_delete_black_24dp)
        }
    }
}