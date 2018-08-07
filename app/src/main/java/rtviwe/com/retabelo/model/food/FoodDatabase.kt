package rtviwe.com.retabelo.model.food

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rtviwe.com.retabelo.model.SingletonHolder

@Database(entities = [FoodEntry::class], version = 1, exportSchema = false)
@TypeConverters(FoodTypeConverter::class)
abstract class FoodDatabase : RoomDatabase() {

    companion object : SingletonHolder<FoodDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                FoodDatabase::class.java,
                "Foods.db")
                .build()
    })

    abstract fun foodDao(): FoodDao
}