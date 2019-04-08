package com.example.smartalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alarm_table")
data class Alarm(
    val day: Int,
    val fromAddress: String,
    val toAddress: String,
    val departTime: Long,
    val alarmTime: Long,
    val isActive: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}