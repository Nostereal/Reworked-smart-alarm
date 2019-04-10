package com.example.smartalarm.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartalarm.R
import com.example.smartalarm.adapters.AlarmAdapter
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.viewmodels.AlarmViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ADD_REQUEST = 1
        private const val EDIT_REQUEST = 2
    }

    private var currentEditAlarmDialog: EditAlarmDialog? = null
    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
//                intent.putExtra(AddEditAlarmActivity.EXTRA_DAY, alarm.day)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_FROM_ADDRESS, alarm.fromAddress)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_TO_ADDRESS, alarm.toAddress)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_DEPART_TIME, alarm.departTime)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_ALARM_TIME, alarm.alarmTime)
//                intent.putExtra(AddEditAlarmActivity.EXTRA_ID, alarm.id)
//
//                startActivityForResult(intent, EDIT_REQUEST)

                //openEditAlarm()
            }
        })
    }

    private fun openEditAlarm(alarm: Alarm) {
        currentEditAlarmDialog = EditAlarmDialog(activity = AppCompatActivity(), alarm = alarm) {
            alarm.id = it
            currentEditAlarmDialog = null

        }
    }
}
