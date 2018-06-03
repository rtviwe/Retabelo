package rtviwe.com.retabelo.database.favorite

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import rtviwe.com.retabelo.database.DateConverter
import rtviwe.com.retabelo.database.SingletonHolder

@Database(entities = [FavoriteEntry::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    companion object : SingletonHolder<FavoriteDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                FavoriteDatabase::class.java,
                "Favorite.db")
                .build()
    })

    abstract fun favoriteDao(): FavoriteDao
}