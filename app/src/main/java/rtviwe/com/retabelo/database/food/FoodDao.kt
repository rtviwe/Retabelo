package rtviwe.com.retabelo.database.food

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(foodEntry: FoodEntry)

    @Query("SELECT * FROM food")
    fun getAllFood(): Flowable<List<FoodEntry>>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getFoodById(id: Int): Flowable<List<FoodEntry>>

    @Query("DELETE FROM food")
    fun nukeFoodTable()

    @Update
    fun updateFood(foodEntry: FoodEntry)

    @Delete
    fun deleteFood(foodEntry: FoodEntry)
}