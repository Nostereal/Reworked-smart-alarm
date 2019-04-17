package com.example.smartalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.receivers.AlarmReceiver
import com.example.smartalarm.ui.MainActivity

const val ALARM_ID = "com.example.smartalarm.ALARM_ID"

fun getFormattedTime(passedSeconds: Int): String {
    val hours = (passedSeconds / 3600) % 24
    val minutes = (passedSeconds / 60) % 60

    val format = "%02d:%02d"
    return String.format(format, hours, minutes)
}

fun Context.showRemainingTimeMessage(triggerInMinutes: Int) {
    Toast.makeText(applicationContext,
        "Alarm was set to ${getFormattedTime(triggerInMinutes * 60)}", Toast.LENGTH_LONG).show()
}

fun Context.setupAlarmClock(alarm: Alarm, triggerInSeconds: Int) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val targetMS = System.currentTimeMillis() + triggerInSeconds * 1000
    AlarmManagerCompat.setAlarmClock(alarmManager, targetMS, getOpenAlarmTabIntent(), getAlarmIntent(alarm))
}

fun Context.getAlarmIntent(alarm: Alarm): PendingIntent {
    val intent = Intent(this, AlarmReceiver::class.java)
    intent.putExtra(ALARM_ID, alarm.id)
    return PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

fun Context.getOpenAlarmTabIntent() : PendingIntent {
    val intent = Intent(this, MainActivity::class.java)
    return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}