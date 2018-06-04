package rtviwe.com.retabelo.main.fragments.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import rtviwe.com.retabelo.database.food.FoodDao
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.database.food.FoodEntry
import java.util.concurrent.Executors

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    private val executor = Executors.newFixedThreadPool(2)

    var foodDatabase: FoodDatabase = FoodDatabase.getInstance(this.getApplication())
    var foodsDao: FoodDao = foodDatabase.foodDao()

    fun deleteFood(foodEntry: FoodEntry) {
        executor.execute { foodsDao.deleteFood(foodEntry) }
    }
}