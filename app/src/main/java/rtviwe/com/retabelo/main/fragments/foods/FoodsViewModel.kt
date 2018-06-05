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
    val foodsDao: FoodDao = foodDatabase.foodDao()

    val foodsList: Flowable<PagedList<FoodEntry>> = RxPagedListBuilder(
            foodsDao.getAllFood(),
            42
    ).buildFlowable(BackpressureStrategy.LATEST)

    fun deleteFood(position: Int, foodsAdapter: FoodsAdapter) {
        Flowable.just(position)
                .observeOn(Schedulers.io())
                .subscribe {
                    val foods = foodsAdapter.foods!!
                    val selectedFood = foods[position]
                    foodsDao.deleteFood(selectedFood)
                }
    }
}