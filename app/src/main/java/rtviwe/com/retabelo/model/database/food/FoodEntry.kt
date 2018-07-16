package rtviwe.com.retabelo.model.database.food

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import rtviwe.com.retabelo.model.Entry

@Entity(tableName = "food")
data class FoodEntry(
        @PrimaryKey(autoGenerate = true)
        override var id: Int,
        var name: String,
        var foodType: FoodType
) : Entry