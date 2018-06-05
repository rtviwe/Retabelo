package rtviwe.com.retabelo.database.recipe

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntry(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name: String,
        var description: String,
        var isFavorite: Boolean
)