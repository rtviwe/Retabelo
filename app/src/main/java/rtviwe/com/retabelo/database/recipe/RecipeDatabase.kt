package rtviwe.com.retabelo.database.recipe

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import rtviwe.com.retabelo.database.SingletonHolder

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