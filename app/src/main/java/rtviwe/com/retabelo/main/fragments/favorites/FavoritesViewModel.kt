package rtviwe.com.retabelo.main.fragments.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.recipe.RecipeDao
import rtviwe.com.retabelo.database.recipe.RecipeDatabase

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    var recipeDatabase: RecipeDatabase = RecipeDatabase.getInstance(this.getApplication())
    var recipeDao: RecipeDao = recipeDatabase.recipeDao()

    fun removeFromFavorite(position: Int, adapter: FavoritesAdapter) {
        Flowable.just(position)
                .observeOn(Schedulers.io())
                .subscribe {
                    val favorites = adapter.favorites!!
                    val deletedFavorite = favorites[position]
                    recipeDao.deleteRecipe(deletedFavorite)
                }
    }
}