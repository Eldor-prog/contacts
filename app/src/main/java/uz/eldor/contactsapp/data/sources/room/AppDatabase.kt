package uz.eldor.contactsapp.data.sources.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.eldor.contactsapp.data.sources.room.dao.GroupDao
import uz.eldor.contactsapp.data.sources.room.dao.StudentDao
import uz.eldor.contactsapp.data.sources.room.entity.GroupData
import uz.eldor.contactsapp.data.sources.room.entity.StudentData

@Database(entities = [GroupData::class,StudentData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao():GroupDao
    abstract fun studentDao():StudentDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}