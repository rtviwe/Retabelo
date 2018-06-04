package rtviwe.com.retabelo.main.fragments.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import rtviwe.com.retabelo.database.recipe.RecipeDao
import rtviwe.com.retabelo.database.recipe.RecipeDatabase
import rtviwe.com.retabelo.database.recipe.RecipeEntry
import java.util.concurrent.Executors

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    private val executor = Executors.newFixedThreadPool(2)

    var recipeDatabase: RecipeDatabase = RecipeDatabase.getInstance(this.getApplication())
    var recipeDao: RecipeDao = recipeDatabase.recipeDao()

    fun removeFromFavorite(recipeEntry: RecipeEntry) {
        executor.execute {
            recipeDao.getRecipeById(recipeEntry.id)
                    .subscribe { recipeDao.deleteRecipe(it[0]) }
        }
    }
}