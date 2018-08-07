package rtviwe.com.retabelo.model.recipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rtviwe.com.retabelo.model.SingletonHolder

@Database(entities = [RecipeEntry::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    companion object : SingletonHolder<RecipeDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                RecipeDatabase::class.java,
                "Recipes.db")
                .build()
    })

    abstract fun recipeDao(): RecipeDao
}