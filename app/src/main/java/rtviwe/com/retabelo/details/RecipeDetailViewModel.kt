package rtviwe.com.retabelo.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Completable
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry

class RecipeDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDao = RecipeDatabase.getInstance(app).recipeDao()

    fun changeFavorite(name: String): Completable {
        return Completable.fromAction {
            val item = recipesDao.findRecipeByName(name)
            if (item != null) {
                RecipeEntry.changeFavorite(recipesDao, item)
            }
        }
    }
}