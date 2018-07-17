package rtviwe.com.retabelo.main.fragments.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.recipe.RecipeDao
import rtviwe.com.retabelo.database.recipe.RecipeDatabase
import rtviwe.com.retabelo.database.recipe.RecipeEntry

class FavoritesViewModel(app: Application) : AndroidViewModel(app) {

    private val recipesDatabase: RecipeDatabase = RecipeDatabase.getInstance(this.getApplication())
    private val recipesDao: RecipeDao = recipesDatabase.recipeDao()

    private var removedLastFavorite: RecipeEntry? = null

    val recipesList: Flowable<PagedList<RecipeEntry>> = RxPagedListBuilder(
            recipesDao.getAllFavoriteRecipes(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun removeFromFavorite(recipeEntry: RecipeEntry) {
        Completable.create {
            recipesDao.deleteRecipe(recipeEntry)
            removedLastFavorite = recipeEntry
            it.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun restoreLastFavorite() {
        if (removedLastFavorite != null) {
            Completable.create {
                recipesDao.insertRecipe(removedLastFavorite!!)
                it.onComplete()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
    }
}