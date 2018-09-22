package rtviwe.com.retabelo.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.launch
import rtviwe.com.retabelo.model.recipe.RecipeDao
import rtviwe.com.retabelo.model.recipe.RecipeDatabase
import rtviwe.com.retabelo.model.recipe.RecipeEntry

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDao: RecipeDao = RecipeDatabase.getInstance(app).recipeDao()

    private var recipesList: Flowable<PagedList<RecipeEntry>> = RxPagedListBuilder(
            recipesDao.getAllFavoriteRecipes(),
            20
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun subscribeFavoritesAdapter(adapter: FavoritesAdapter) {
        launch {
            recipesList.subscribe(adapter::submitList)
        }
    }

    fun subscribeFavoritesAdapterBySearch(adapter: FavoritesAdapter, text: String) {
        launch {
            val searchedRecipesList: Flowable<PagedList<RecipeEntry>> = RxPagedListBuilder(
                    recipesDao.getFavoriteRecipesByName("%$text%"),
                    20
            ).buildFlowable(BackpressureStrategy.LATEST)

            searchedRecipesList.subscribe(adapter::submitList)
        }
    }
}