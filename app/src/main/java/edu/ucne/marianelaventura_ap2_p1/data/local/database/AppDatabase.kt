package edu.ucne.marianelaventura_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import edu.ucne.marianelaventura_ap2_p1.data.local.dao.EntradasHuacalesDao
import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradasHuacalesEntity
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Database(
    entities = [EntradasHuacalesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entradasHuacalesDao(): EntradasHuacalesDao
}

