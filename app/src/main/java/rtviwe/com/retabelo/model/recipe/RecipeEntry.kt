package rtviwe.com.retabelo.model.recipe

import android.widget.CheckBox
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.model.Entry

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
            CompositeDisposable().add(Completable.fromAction {
                        val searchedRecipe: RecipeEntry? = recipesDao.findRecipeByName(item.name)
                        if (searchedRecipe == null || !searchedRecipe.isFavorite) {
                            item.isFavorite = true
                            recipesDao.insertRecipe(item)
                        } else {
                            item.isFavorite = false
                            recipesDao.deleteRecipeByName(item.name)
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }

        fun setIsFavorite(recipesDao: RecipeDao, name: String, icon: CheckBox): Boolean {
            return CompositeDisposable().add(Completable.fromAction {
                icon.isChecked = recipesDao.findRecipeByName(name) != null
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }
}