package com.example.smartalarm.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class AlarmRepository(application: Application) {
    private var alarmDao: AlarmDao
    private var allAlarms: LiveData<List<Alarm>>

    private val uiScope = CoroutineScope(Dispatchers.IO)

    init {
        val database: AlarmDatabase = AlarmDatabase.getInstance(application.applicationContext)!!
        alarmDao = database.alarmDao()
        allAlarms = alarmDao.getAllAlarms()
    }

    fun getAllAlarms() = allAlarms

    fun insert(alarm: Alarm) {
        uiScope.launch { alarmDao.insert(alarm) }
    }

    fun delete(alarm: Alarm) {
        uiScope.launch { alarmDao.delete(alarm) }
    }

    fun deleteAllNotes() {
        uiScope.launch { alarmDao.deleteAllAlarms() }
    }

    fun update(alarm: Alarm) {
        uiScope.launch { alarmDao.update(alarm) }
    }

    fun getAlarmById(id: Int) = alarmDao.getAlarmById(id)
}