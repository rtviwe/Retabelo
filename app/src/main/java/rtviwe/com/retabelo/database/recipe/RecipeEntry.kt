package rtviwe.com.retabelo.database.recipe

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.Entry

@IgnoreExtraProperties
@Entity(tableName = "recipe")
data class RecipeEntry(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        var name: String,
        var body: String,
        var isFavorite: Boolean
) : Entry {

    @Ignore
    constructor() : this(0, "", "", false)

    companion object {
        fun changeFavorite(recipesDao: RecipeDao, item: RecipeEntry) {
            Completable.create {
                val searchedRecipe: RecipeEntry? = recipesDao.findRecipeByName(item.name)
                if (searchedRecipe == null || !searchedRecipe.isFavorite) {
                    item.isFavorite = true
                    recipesDao.insertRecipe(item)
                } else {
                    item.isFavorite = false
                    recipesDao.deleteRecipeByName(item.name)
                }
                it.onComplete()
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }

        fun cache(recipesDao: RecipeDao, item: RecipeEntry) {

        }
    }
}