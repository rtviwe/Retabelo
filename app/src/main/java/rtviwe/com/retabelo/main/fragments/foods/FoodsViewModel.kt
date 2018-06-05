package rtviwe.com.retabelo.main.fragments.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.food.FoodDao
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    private val foodDatabase: FoodDatabase = FoodDatabase.getInstance(getApplication())
    private val foodsDao: FoodDao = foodDatabase.foodDao()

    val foodsList: Flowable<PagedList<FoodEntry>> = RxPagedListBuilder(
            foodsDao.getAllFoods(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun insertFood(foodEntry: FoodEntry) {
        Flowable.just(foodEntry)
                .observeOn(Schedulers.io())
                .subscribe {
                    foodsDao.insertFood(foodEntry)
                }
    }

    fun deleteFood(foodEntry: FoodEntry) {
        Flowable.just(foodEntry)
                .observeOn(Schedulers.io())
                .subscribe {
                    foodsDao.deleteFood(foodEntry)
                }
    }
}