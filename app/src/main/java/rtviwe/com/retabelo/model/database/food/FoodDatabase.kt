package rtviwe.com.retabelo.model.database.food

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
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