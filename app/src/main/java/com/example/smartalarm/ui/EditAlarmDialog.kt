package com.example.smartalarm.ui

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.smartalarm.R
import com.example.smartalarm.addBit
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.getFormattedTime
import com.example.smartalarm.removeBit
import com.example.smartalarm.viewmodels.AlarmViewModel
import kotlinx.android.synthetic.main.dialog_edit_alarm.*
import kotlinx.android.synthetic.main.dialog_edit_alarm.view.*

class EditAlarmDialog(val activity: AppCompatActivity, val alarm: Alarm, val callback: (alarmId: Int) -> Unit) {
    private val view = activity.layoutInflater.inflate(R.layout.dialog_edit_alarm, null)

    private val alarmViewModel by lazy { ViewModelProviders.of(activity).get(AlarmViewModel::class.java) }

    init {
        updateAlarmTime()

        view.apply {
            edit_alarm_time.setOnClickListener {
                TimePickerDialog(
                    context,
                    timeSetListener,
                    alarm.timeInMinutes / 60,
                    alarm.timeInMinutes % 60,
                    true
                ).show()
            }

            edit_alarm_vibrate.isChecked = alarm.isEnabled
            edit_alarm_vibrate_holder.setOnClickListener {
                edit_alarm_vibrate.toggle()
                alarm.isVibrateEnabled = edit_alarm_vibrate.isChecked
                alarmViewModel.update(alarm)
            }

            edit_alarm_label.setText(alarm.label)

            val dayLetters = activity.resources.getStringArray(R.array.days_letters).toList() as ArrayList<String>
            val dayIndexes = arrayListOf(0, 1, 2, 3, 4, 5, 6)

            dayIndexes.forEach {
                // each day is a bit (0 - disabled or 1 - enabled)
                val pow = Math.pow(2.0, it.toDouble()).toInt()
                val day = activity.layoutInflater.inflate(R.layout.alarm_day, edit_alarm_days_holder, false) as TextView
                day.text = dayLetters[it]

                val isDayChecked = alarm.days and pow != 0
                // TODO: change background color

                day.setOnClickListener {
                    val selectDay = alarm.days and pow != 0
                    if (selectDay) {
                        alarm.days = alarm.days.addBit(pow)
                    } else {
                        alarm.days = alarm.days.removeBit(pow)
                    }
                    // TODO: change background color
                }

                edit_alarm_days_holder.addView(day)
            }
        }

        AlertDialog.Builder(activity)
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .create().apply {
                if (activity.isDestroyed || activity.isFinishing) {
                    return@apply
                }

                setCanceledOnTouchOutside(true)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                show()

                getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    if (alarm.days == 0) {
                        Toast.makeText(activity.baseContext, "No days selected", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    alarm.label = edit_alarm_label.text.toString()

                    val alarmId = alarmViewModel.getAlarmById(alarm.id).id
                    if (alarmId == -1) {
                        alarmViewModel.insert(alarm)
                    } else {
                        alarmViewModel.update(alarm)
                    }
                    callback(alarmId)
                    dismiss()
                }
            }
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        alarm.timeInMinutes = hourOfDay * 60 + minute
        alarmViewModel.update(alarm)
        updateAlarmTime()
    }

    private fun updateAlarmTime() {
        view.edit_alarm_time.text = getFormattedTime(alarm.timeInMinutes)
    }
}