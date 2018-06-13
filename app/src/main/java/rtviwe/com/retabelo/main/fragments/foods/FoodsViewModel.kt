package rtviwe.com.retabelo.main.fragments.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.food.FoodDao
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    private val foodDatabase: FoodDatabase = FoodDatabase.getInstance(getApplication())
    private val foodsDao: FoodDao = foodDatabase.foodDao()

    private var removedLastFood: FoodEntry? = null

    val foodsList: Flowable<PagedList<FoodEntry>> = RxPagedListBuilder(
            foodsDao.getAllFoods(),
            50
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun insertFood(foodEntry: FoodEntry) {
        Completable.create {
                    foodsDao.insertFood(foodEntry)
                    it.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun deleteFood(foodEntry: FoodEntry) {
        Completable.create {
                    foodsDao.deleteFood(foodEntry)
                    removedLastFood = foodEntry
                    it.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun restoreFood() {
        if (removedLastFood != null) {
            Completable.create {
                        foodsDao.insertFood(removedLastFood!!)
                        it.onComplete()
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
        }
    }
}