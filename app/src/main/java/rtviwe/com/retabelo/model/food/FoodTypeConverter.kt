package rtviwe.com.retabelo.model.food

import androidx.room.TypeConverter
import rtviwe.com.retabelo.R

class FoodTypeConverter {

    @TypeConverter
    fun toInt(type: FoodType) = type.ordinal

    @TypeConverter
    fun toFoodType(id: Int) = FoodType.values().single { it.ordinal == id }

    companion object {

        val typesDrawable = arrayListOf(
                R.drawable.other,
                R.drawable.liquid,
                R.drawable.bread,
                R.drawable.alcohol,
                R.drawable.fruit,
                R.drawable.vegetable,
                R.drawable.grocery,
                R.drawable.fish,
                R.drawable.meat,
                R.drawable.sweet,
                R.drawable.cheese,
                R.drawable.cereals)
    }
}