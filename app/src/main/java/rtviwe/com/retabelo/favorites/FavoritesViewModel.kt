package rtviwe.com.retabelo.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDatabase: RecipeDatabase = RecipeDatabase.getInstance(this.getApplication())
    private val recipesDao: RecipeDao = recipesDatabase.recipeDao()

    val recipesList: Flowable<PagedList<RecipeEntry>> = RxPagedListBuilder(
            recipesDao.getAllFavoriteRecipes(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)
}