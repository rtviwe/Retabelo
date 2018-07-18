package rtviwe.com.retabelo.database.recipe

import android.content.Context
import android.content.Intent
import rtviwe.com.retabelo.details.RecipeDetail

object RecipePresenter {

    fun openActivity(context: Context, item: RecipeEntry) {
        val intent = Intent(context, RecipeDetail::class.java)
        intent.putExtra(RecipeDetail.EXTRA_NAME, item.name)
        intent.putExtra(RecipeDetail.EXTRA_BODY, item.body)
        context.startActivity(intent)
    }
}