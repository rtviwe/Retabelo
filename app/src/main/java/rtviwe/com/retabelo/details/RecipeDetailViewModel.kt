package rtviwe.com.retabelo.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.experimental.launch
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry

class RecipeDetailViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDao = RecipeDatabase.getInstance(app).recipeDao()

    fun changeFavorite(name: String, body: String) {
        launch {
            val item = recipesDao.findRecipeByName(name)

            if (item == null) {
                val newItem = RecipeEntry(0, name, body, true)
                recipesDao.insertRecipe(newItem)
            } else {
                RecipeEntry.changeFavorite(recipesDao, item)
            }
        }
    }
}