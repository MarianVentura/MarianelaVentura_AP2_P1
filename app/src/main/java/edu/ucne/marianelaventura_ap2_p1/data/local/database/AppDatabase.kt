package edu.ucne.marianelaventura_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import edu.ucne.marianelaventura_ap2_p1.data.local.dao.EntradaHuacalDao
import edu.ucne.marianelaventura_ap2_p1.data.local.entities.EntradaHuacalEntity

@Database(
    entities = [EntradaHuacalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entradaHuacalDao(): EntradaHuacalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "huacales_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}