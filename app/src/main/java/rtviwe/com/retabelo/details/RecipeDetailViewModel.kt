package rtviwe.com.retabelo.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.experimental.launch
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry

class RecipeDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDao = RecipeDatabase.getInstance(app).recipeDao()

    fun changeFavorite(name: String) {
        launch {
            val item = recipesDao.findRecipeByName(name)
            item?.let {
                RecipeEntry.changeFavorite(recipesDao, it)
            }
        }
    }
}