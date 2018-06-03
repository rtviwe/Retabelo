package rtviwe.com.retabelo.database

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(timeStamp: Long) = Date(timeStamp)

    @TypeConverter
    fun toTimeStamp(date: Date) = date.time
}