package rtviwe.com.retabelo.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import rtviwe.com.retabelo.model.food.FoodDao
import rtviwe.com.retabelo.model.food.FoodDatabase
import rtviwe.com.retabelo.model.food.FoodEntry

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    private val foodDatabase: FoodDatabase = FoodDatabase.getInstance(getApplication())
    private val foodsDao: FoodDao = foodDatabase.foodDao()

    private var removedLastFood: FoodEntry? = null

    val foodsList: Flowable<PagedList<FoodEntry>> = RxPagedListBuilder(
            foodsDao.getAllFoods(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun insertFood(foodEntry: FoodEntry): Completable {
        return Completable.fromAction {
            foodsDao.insertFood(foodEntry)
        }
    }

    fun deleteFood(foodEntry: FoodEntry): Completable {
        return Completable.fromAction {
            foodsDao.deleteFood(foodEntry)
            removedLastFood = foodEntry
        }
    }

    fun restoreFood(): Completable? {
        if (removedLastFood != null) {
            return Completable.fromAction {
                foodsDao.insertFood(removedLastFood!!)
            }
        }
        return null
    }
}