package com.example.smartalarm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.smartalarm.R
import com.example.smartalarm.data.DaysOfWeek
import kotlinx.android.synthetic.main.activity_add_edit_alarm.*
import java.util.*
import java.util.zip.Inflater

class AddEditAlarmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DAY = "com.example.smartalarm.EXTRA_DAY"
        const val EXTRA_FROM_ADDRESS = "com.example.smartalarm.EXTRA_FROM_ADDRESS"
        const val EXTRA_TO_ADDRESS = "com.example.smartalarm.EXTRA_TO_ADDRESS"
        const val EXTRA_DEPART_TIME = "com.example.smartalarm.EXTRA_DEPART_TIME"
        const val EXTRA_ALARM_TIME = "com.example.smartalarm.EXTRA_ALARM_TIME"
        const val EXTRA_ID = "com.example.smartalarm.EXTRA_ID"

        val daysList: List<String> = listOf(
            DaysOfWeek.MONDAY.toString(),
            DaysOfWeek.TUESDAY.toString(),
            DaysOfWeek.WEDNESDAY.toString(),
            DaysOfWeek.THURSDAY.toString(),
            DaysOfWeek.SATURDAY.toString(),
            DaysOfWeek.SUNDAY.toString()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_alarm)

        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Alarm"
            fromAddressEditText.setText(intent.getStringExtra(EXTRA_FROM_ADDRESS))
            toAddressEditText.setText(intent.getStringExtra(EXTRA_TO_ADDRESS))

            val days = resources.getStringArray(R.array.days)
            val currentDayId = days.indexOf(intent.getStringExtra(EXTRA_DAY))
            selectDaySpinner.setSelection(currentDayId)

            var cal: Calendar = intent.getSerializableExtra(EXTRA_DEPART_TIME) as Calendar
            departTimePickerTV.text = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
            cal = intent.getSerializableExtra(EXTRA_ALARM_TIME) as Calendar
            alarmTimePickerTV.text = "${cal.get(Calendar.HOUR_OF_DAY)}:${cal.get(Calendar.MINUTE)}"
        } else
            title = "Add Alarm"

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_alarm_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.saveAlarm -> {
                saveAlarm()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAlarm() {
        val fromAddress = fromAddressEditText.text.toString()
        val toAddress = toAddressEditText.text.toString()
        val day = selectDaySpinner.selectedItem.toString()

        val cal = Calendar.getInstance()
        val daysOfWeek = cal.get(Calendar.DAY_OF_MONTH)
    }
}
