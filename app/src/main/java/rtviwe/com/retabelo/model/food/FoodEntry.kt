package rtviwe.com.retabelo.model.food

import androidx.room.Entity
import androidx.room.PrimaryKey
import rtviwe.com.retabelo.model.Entry

@Entity(tableName = "food")
data class FoodEntry(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        var name: String,
        var foodType: FoodType
) : Entry