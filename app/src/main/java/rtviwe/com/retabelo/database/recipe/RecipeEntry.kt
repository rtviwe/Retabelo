package rtviwe.com.retabelo.database.recipe

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import rtviwe.com.retabelo.database.Entry

@Entity(tableName = "recipe")
data class RecipeEntry(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        var name: String,
        var description: String,
        var isFavorite: Boolean
) : Entry