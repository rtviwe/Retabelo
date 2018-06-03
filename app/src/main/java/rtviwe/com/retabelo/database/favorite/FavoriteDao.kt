package rtviwe.com.retabelo.database.favorite

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(dishEntry: FavoriteEntry)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): LiveData<List<FavoriteEntry>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getFavoriteById(id: Int): LiveData<FavoriteEntry>

    @Update
    fun updateFavorite(favoriteEntry: FavoriteEntry)

    @Delete
    fun deleteFavorite(favoriteEntry: FavoriteEntry)
}