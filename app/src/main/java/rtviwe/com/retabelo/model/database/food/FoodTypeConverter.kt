package rtviwe.com.retabelo.model.database.food

import android.arch.persistence.room.TypeConverter

class FoodTypeConverter {

    @TypeConverter
    fun toInt(type: FoodType) = type.ordinal

    @TypeConverter
    fun toFoodType(id: Int) = FoodType.values().single { it.ordinal == id }
}