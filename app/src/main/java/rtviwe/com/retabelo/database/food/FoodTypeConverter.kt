package rtviwe.com.retabelo.database.food

import android.arch.persistence.room.TypeConverter

import rtviwe.com.retabelo.database.food.FoodType.*

class FoodTypeConverter {

    @TypeConverter
    fun toInt(type: FoodType) = when (type) {
        ANY -> 0
        WATER -> 1
        BREAD -> 2
        ALCOHOL -> 3
        FRUIT -> 4
        VEGETABLE -> 5
        GROCERY -> 6
        MEAT -> 7
        FISH -> 8
        SWEET -> 9
        CHEESE -> 10
        CEREALS -> 11
        SAUCE -> 12
        SPICE -> 13
    }

    @TypeConverter
    fun toFoodType(id: Int) = when (id) {
        0 -> ANY
        1 -> WATER
        2 -> BREAD
        3 -> ALCOHOL
        4 -> FRUIT
        5 -> VEGETABLE
        6 -> GROCERY
        7 -> MEAT
        8 -> FISH
        9 -> SWEET
        10 -> CHEESE
        11 -> CEREALS
        12 -> SAUCE
        13 -> SPICE
        else -> null
    }
}