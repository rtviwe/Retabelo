package rtviwe.com.retabelo.database.favorite

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntry(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var name: String,
        var description: String
)