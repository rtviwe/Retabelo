package rtviwe.com.retabelo.model.database.food

import android.arch.paging.DataSource
import android.arch.persistence.room.*

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(foodEntry: FoodEntry)

    @Query("SELECT * FROM food")
    fun getAllFoods(): DataSource.Factory<Int, FoodEntry>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getFoodsById(id: Int): DataSource.Factory<Int, FoodEntry>

    @Query("SELECT * FROM food WHERE foodType = :foodType")
    fun getFoodsByType(foodType: FoodType): DataSource.Factory<Int, FoodEntry>

    @Update
    fun updateFood(foodEntry: FoodEntry)

    @Delete
    fun deleteFood(foodEntry: FoodEntry)

    @Query("DELETE FROM food")
    fun nukeFoodTable()
}