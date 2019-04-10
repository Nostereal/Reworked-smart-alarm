package com.example.smartalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class Alarm(
    val day: String,
    val fromAddress: String,
    val toAddress: String,
    val departTimeInMinutes: Int,
    val timeInMinutes: Int,
    val isEnabled: Boolean,
    val label: String,
    val isVibrateEnabled: Boolean,
    val soundTitle: String,
    val soundUri: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}