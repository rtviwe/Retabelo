package rtviwe.com.retabelo.main.fragments.foods

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import rtviwe.com.retabelo.database.food.FoodDatabase
import rtviwe.com.retabelo.database.food.FoodEntry

class FoodsViewModel(app: Application) : AndroidViewModel(app) {

    var foodDatabase: FoodDatabase = FoodDatabase.getInstance(this.getApplication())
    var foods: LiveData<List<FoodEntry>> = foodDatabase.foodDao().getAllFood()
}