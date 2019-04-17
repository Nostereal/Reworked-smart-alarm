package com.example.smartalarm.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class Alarm(
    var days: Int,
    var fromAddress: String,
    var toAddress: String,
    var departTimeInMinutes: Int,
    var timeInMinutes: Int,
    var isEnabled: Boolean,
    var label: String,
    var isVibrateEnabled: Boolean,
    var soundTitle: String,
    var soundUri: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}