package rtviwe.com.retabelo.foods

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.launch
import rtviwe.com.retabelo.model.food.FoodDao
import rtviwe.com.retabelo.model.food.FoodDatabase
import rtviwe.com.retabelo.model.food.FoodEntry

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    private val foodsDao: FoodDao = FoodDatabase.getInstance(app).foodDao()

    var removedLastFood: FoodEntry? = null

    private val foodsList: Flowable<PagedList<FoodEntry>> = RxPagedListBuilder(
            foodsDao.getAllFoods(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun subscribeFoodsAdapter(adapter: FoodsAdapter) {
        launch {
            foodsList.subscribe(adapter::submitList)
        }
    }

    fun insertFood(foodEntry: FoodEntry) {
        launch {
            foodsDao.insertFood(foodEntry)
        }
    }

    fun deleteFood(foodEntry: FoodEntry) {
        removedLastFood = foodEntry

        launch {
            foodsDao.deleteFood(foodEntry)
        }
    }

    fun restoreFood() {
        removedLastFood?.let {
            launch {
                foodsDao.insertFood(removedLastFood!!)
            }
        }
    }
}