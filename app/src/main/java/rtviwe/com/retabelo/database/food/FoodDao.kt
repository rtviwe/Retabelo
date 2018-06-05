package rtviwe.com.retabelo.database.food

import android.arch.paging.DataSource
import android.arch.persistence.room.*

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(foodEntry: FoodEntry)

    @Query("SELECT * FROM food")
    fun getAllFood(): DataSource.Factory<Int, FoodEntry>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getFoodById(id: Int): DataSource.Factory<Int, FoodEntry>

    @Update
    fun updateFood(foodEntry: FoodEntry)

    @Delete
    fun deleteFood(foodEntry: FoodEntry)
}