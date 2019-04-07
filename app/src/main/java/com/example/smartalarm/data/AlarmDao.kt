package com.example.smartalarm.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlarmDao {

    @Insert
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)

    @Update
    fun update(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    fun deleteAllAlarms()

    @Query("SELECT * FROM alarm_table ORDER BY day ASC")
    fun getAllAlarms() : LiveData<List<Alarm>>
}