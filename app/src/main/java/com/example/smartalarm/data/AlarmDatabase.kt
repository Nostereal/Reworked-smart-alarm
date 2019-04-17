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
import java.io.Serializable
import java.util.*

@Database(entities = [Alarm::class], version = 2)
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
            val calDepart = Calendar.getInstance()
            calDepart.set(Calendar.HOUR_OF_DAY, 12)
            calDepart.set(Calendar.MINUTE, 10)
            val departTimeInMinutes = calDepart.get(Calendar.HOUR_OF_DAY) * 60 + calDepart.get(Calendar.MINUTE)

            val calAlarm = Calendar.getInstance()
            calAlarm.set(Calendar.HOUR_OF_DAY, 11)
            calAlarm.set(Calendar.MINUTE, 20)
            val alarmTimeInMinutes = calAlarm.get(Calendar.HOUR_OF_DAY) * 60 + calAlarm.get(Calendar.MINUTE)


            withContext(Dispatchers.IO) {
                alarmDao?.insert(Alarm(1, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", departTimeInMinutes,
                    alarmTimeInMinutes, true, "First para",
                    false, "Dingdong", "soundUriExample"))
                alarmDao?.insert(Alarm(1, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", departTimeInMinutes,
                    alarmTimeInMinutes, true, "Not first para",
                    false, "Dingdong", "soundUriExample"))
                alarmDao?.insert(Alarm(3, "800-летия Москвы, 28к1",
                    "Прянишникова, 2а", departTimeInMinutes,
                    alarmTimeInMinutes, true, "Some text in label",
                    false, "Dingdong", "soundUriExample"))
            }
        }
    }
}