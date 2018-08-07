package rtviwe.com.retabelo.model.food

import androidx.room.TypeConverter

class FoodTypeConverter {

    @TypeConverter
    fun toInt(type: FoodType) = type.ordinal

    @TypeConverter
    fun toFoodType(id: Int) = FoodType.values().single { it.ordinal == id }
}