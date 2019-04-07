package com.example.smartalarm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao() : AlarmDao

    companion object {
        private var instance: AlarmDatabase? = null

        fun getInstance(context: Context) : AlarmDatabase? {
            if (instance == null) {
                synchronized(AlarmDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AlarmDatabase::class.java, "alarm_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val uiScope = CoroutineScope(Dispatchers.IO)

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                uiScope.launch { populateDb(instance) }
            }
        }

        suspend fun populateDb(db: AlarmDatabase?) {
            val alarmDao = db?.alarmDao()
            withContext(Dispatchers.IO) {
                alarmDao?.insert(Alarm(DaysOfWeek.MONDAY, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", 1555056000000,
                    1555052400000, true))
                alarmDao?.insert(Alarm(DaysOfWeek.WEDNESDAY, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", 1555056000000,
                    1555052400000, true))
                alarmDao?.insert(Alarm(DaysOfWeek.FRIDAY, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", 1555056000000,
                    1555052400000, true))
            }
        }
    }
}