package rtviwe.com.retabelo.model.recipe

import android.webkit.WebView
import android.widget.CheckBox
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.coroutines.experimental.launch
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
            launch {
                val isFavorite = recipesDao.isFavoriteByName(item.name)
                if (!isFavorite) {
                    item.isFavorite = true
                    recipesDao.insertRecipe(item)
                } else {
                    item.isFavorite = false
                    recipesDao.deleteRecipeByName(item.name)
                }
            }
        }

        fun setIconAndItemFavorite(recipesDao: RecipeDao, name: String, icon: CheckBox) {
            launch {
                icon.isChecked = recipesDao.findRecipeByName(name) != null
            }
        }

        fun loadWebView(webView: WebView, text: String) {
            val htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />$text"
            webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null)
        }
    }
}