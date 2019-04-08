package com.example.smartalarm.ui

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
    }
}
