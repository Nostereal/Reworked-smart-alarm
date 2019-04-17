package com.example.smartalarm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartalarm.R
import com.example.smartalarm.adapters.AlarmAdapter
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.setupAlarmClock
import com.example.smartalarm.showRemainingTimeMessage
import com.example.smartalarm.viewmodels.AlarmViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ADD_REQUEST = 1
        private const val EDIT_REQUEST = 2
        private const val DAY_MINUTES = 24 * 60
    }

    private var currentEditAlarmDialog: EditAlarmDialog? = null
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter = AlarmAdapter()
        recyclerView.adapter = adapter

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel::class.java)
        alarmViewModel.getAllAlarms().observe(this, Observer<List<Alarm>> { alarms ->
            adapter.submitList(alarms)
        })


        adapter.setOnItemClickListener(object : AlarmAdapter.OnItemClickListener {
            override fun onItemCLick(alarm: Alarm) {
//                val intent = Intent(this@MainActivity, AddEditAlarmActivity::class.java)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_DAY, alarm.days)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_FROM_ADDRESS, alarm.fromAddress)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_TO_ADDRESS, alarm.toAddress)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_DEPART_TIME, alarm.departTimeInMinutes)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_ALARM_TIME, alarm.timeInMinutes)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_ID, alarm.id)
//
//                startActivityForResult(intent, EDIT_REQUEST)

                openEditAlarm(alarm)
            }
        })
    }

    private fun openEditAlarm(alarm: Alarm) {
        currentEditAlarmDialog = EditAlarmDialog(activity = AppCompatActivity(), alarm = alarm) {
            alarm.id = it
            currentEditAlarmDialog = null
            alarmViewModel.update(alarm)
            //...
        }
    }

    fun scheduleNextAlarm(alarm: Alarm, showToast: Boolean) {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        for (i in 0..7) {
            val currentDay = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
            val isCorrectDay = alarm.days and 2.0.pow(currentDay).toInt() != 0
            val currentTimeInMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
            if (isCorrectDay && (currentTimeInMinutes < alarm.timeInMinutes || i > 0)) {
                val triggerInMinutes = alarm.timeInMinutes - currentTimeInMinutes + (i * DAY_MINUTES)
                setupAlarmClock(alarm, triggerInMinutes * 60 - calendar.get(Calendar.SECOND))

                if (showToast) {
                    showRemainingTimeMessage(triggerInMinutes)
                }
                break
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
    }
}
