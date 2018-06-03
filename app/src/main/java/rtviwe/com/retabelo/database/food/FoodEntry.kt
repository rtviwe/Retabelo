package rtviwe.com.retabelo.database.food

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntry(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name: String,
        var description: String
)