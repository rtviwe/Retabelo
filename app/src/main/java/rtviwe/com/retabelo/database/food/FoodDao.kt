package rtviwe.com.retabelo.database.food

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(foodEntry: FoodEntry)

    @Query("SELECT * FROM food")
    fun getAllFood(): LiveData<List<FoodEntry>>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getFoodById(id: Int): LiveData<FoodEntry>

    @Update
    fun updateFood(foodEntry: FoodEntry)

    @Delete
    fun deleteFood(foodEntry: FoodEntry)
}