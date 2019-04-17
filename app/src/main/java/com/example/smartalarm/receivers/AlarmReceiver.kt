package com.example.smartalarm.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.smartalarm.ALARM_ID
import com.example.smartalarm.ui.ReminderActivity
import com.example.smartalarm.viewmodels.AlarmViewModel

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(ALARM_ID, -1)
        alarmViewModel = ViewModelProviders.of(context as FragmentActivity).get(AlarmViewModel::class.java)
        val alarm = alarmViewModel.getAlarmById(id)

        Intent(context, ReminderActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(ALARM_ID, id)
            context.startActivity(this)
        }
    }
}
