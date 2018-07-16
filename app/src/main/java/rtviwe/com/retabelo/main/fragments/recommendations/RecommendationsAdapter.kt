package rtviwe.com.retabelo.main.fragments.recommendations

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recommendation_list_item.view.*
import rtviwe.com.retabelo.R
import rtviwe.com.retabelo.database.recipe.Recipe

class RecommendationsAdapter(private val context: Context,
                             options: FirestorePagingOptions<Recipe>)
    : FirestorePagingAdapter<Recipe, RecommendationsAdapter.RecommendationsViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommendation_list_item,
                parent, false)
        return RecommendationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int, model: Recipe) {
        holder.bindTo(model)
    }

    inner class RecommendationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nameTextView = itemView.text_view_recommended_dish
        private var photoImageView = itemView.image_view_recommended_dish
        private var bodyTextView = itemView.text_view_recommended_body

        private val firebaseStorage = FirebaseStorage.getInstance()
        private val firebaseStoragePhotos = firebaseStorage.reference.child("photos")

        fun bindTo(item: Recipe) {
            nameTextView.text = item.name
            bodyTextView.text = item.body.take(509).plus("...")

            val storageReferenceToPhoto = firebaseStoragePhotos.child(item.photo)

            storageReferenceToPhoto.downloadUrl.addOnSuccessListener {
                Picasso.with(context)
                        .load(it)
                        .into(photoImageView)
            }.addOnFailureListener {
                Log.e("RecommendationsAdapter", it.stackTrace.toString())
            }
        }
    }
}