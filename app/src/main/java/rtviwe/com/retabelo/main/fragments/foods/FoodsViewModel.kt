package rtviwe.com.retabelo.main.fragments.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import rtviwe.com.retabelo.database.food.FoodDao
import rtviwe.com.retabelo.database.food.FoodDatabase

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    var foodDatabase: FoodDatabase = FoodDatabase.getInstance(this.getApplication())
    var foodsDao: FoodDao = foodDatabase.foodDao()

    fun deleteFood(position: Int, foodsAdapter: FoodsAdapter) {
        Flowable.just(position)
                .observeOn(Schedulers.io())
                .subscribe {
                    val foods = foodsAdapter.foods!!
                    val deletedFood = foods[position]
                    foodsDao.deleteFood(deletedFood)
                }
    }
}