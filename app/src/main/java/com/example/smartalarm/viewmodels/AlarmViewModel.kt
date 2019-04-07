package com.example.smartalarm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.data.AlarmRepository

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val allAlarms = repository.getAllAlarms()

    fun insert(alarm: Alarm) {
        repository.insert(alarm)
    }

    fun update(alarm: Alarm) {
        repository.update(alarm)
    }

    fun delete(alarm: Alarm) {
        repository.delete(alarm)
    }

    fun deleteAllAlarms(alarm: Alarm) {
        repository.deleteAllNotes()
    }

    fun getAllAlarms() = allAlarms
}