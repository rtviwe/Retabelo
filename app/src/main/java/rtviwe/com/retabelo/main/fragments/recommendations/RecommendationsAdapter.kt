package rtviwe.com.retabelo.main.fragments.recommendations

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import kotlinx.android.synthetic.main.recommendation_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.Recipe

class RecommendationsAdapter(options: FirestorePagingOptions<Recipe>)
    : FirestorePagingAdapter<Recipe, RecommendationsAdapter.RecommendationsViewHolder>(options) {

    private var parentView: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommendation_list_item,
                parent, false)

        parentView = parent

        return RecommendationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int, model: Recipe) {
        holder.bindTo(model)
    }

    inner class RecommendationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameTextView = itemView.text_view_recommended_dish
        private var photoImageView = itemView.image_view_recommended_dish

        fun bindTo(item: Recipe) {
            nameTextView.text = item.name
        }
    }
}