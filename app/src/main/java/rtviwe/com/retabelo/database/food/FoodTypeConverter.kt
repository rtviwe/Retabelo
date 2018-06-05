package rtviwe.com.retabelo.database.food

import android.arch.persistence.room.TypeConverter

class FoodTypeConverter {

    @TypeConverter
    fun toInt(type: FoodType): Int = type.ordinal

    @TypeConverter
    fun toFoodType(id: Int): FoodType = FoodType.values().single { it.ordinal == id }
}