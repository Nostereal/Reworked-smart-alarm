package com.example.smartalarm.ui

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smartalarm.R
import com.example.smartalarm.data.Alarm
import kotlinx.android.synthetic.main.dialog_edit_alarm.view.*

class EditAlarmDialog(val activity: AppCompatActivity, val alarm: Alarm, val callback: (alarmId: Int) -> Unit) {
    private val view = activity.layoutInflater.inflate(R.layout.dialog_edit_alarm, null)

    init {
        updateAlarmTime()

        view.apply {
            edit_alarm_time.setOnClickListener {
                TimePickerDialog(context, timeSetListener, alarm.timeInMinutes / 60, alarm.timeInMinutes % 60, true).show()
            }
        }
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        // alarm.timeInMinutes = hourOfDay * 60 + minute
        updateAlarmTime()
    }

    private fun updateAlarmTime() {
        view.edit_alarm_time.text = getFormattedTime(alarm.timeInMinutes)
    }

    private fun getFormattedTime(passedSeconds: Int): String {
        val hours = (passedSeconds / 3600) % 24
        val minutes = (passedSeconds / 60) % 60

        val format = "%02d:%02d"
        return String.format(format, hours, minutes)
    }
}