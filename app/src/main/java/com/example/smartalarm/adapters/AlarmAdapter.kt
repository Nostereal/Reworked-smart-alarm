package com.example.smartalarm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarm.R
import com.example.smartalarm.data.Alarm
import com.example.smartalarm.getFormattedTime
import com.example.smartalarm.viewmodels.AlarmViewModel

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmHolder>(DIFF_CALLBACK) {
    private var listener: OnItemClickListener? = null
    private lateinit var alarmViewModel: AlarmViewModel

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.days == newItem.days && oldItem.timeInMinutes == newItem.timeInMinutes
                        && oldItem.departTimeInMinutes == newItem.departTimeInMinutes && oldItem.fromAddress == newItem.fromAddress
                        && oldItem.toAddress == newItem.toAddress && oldItem.isEnabled == newItem.isEnabled
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_item, parent, false)
        alarmViewModel = ViewModelProviders.of(parent.context as FragmentActivity).get(AlarmViewModel::class.java)

        return AlarmHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {
        val currentAlarm = getItem(position)
        holder.fromAddress.text = currentAlarm.fromAddress
        holder.toAddress.text = currentAlarm.toAddress
        holder.departTime.text = getFormattedTime(currentAlarm.departTimeInMinutes * 60)
        holder.alarmTime.text = getFormattedTime(currentAlarm.timeInMinutes * 60)
        holder.alarmSwitch.isEnabled = currentAlarm.isEnabled

        holder.alarmSwitch.setOnClickListener {
            holder.alarmSwitch.toggle()
            currentAlarm.isEnabled = !currentAlarm.isEnabled
            alarmViewModel.update(currentAlarm)
        }
    }

    inner class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var fromAddress = itemView.findViewById<TextView>(R.id.alarm_item_from_address)
        internal var toAddress = itemView.findViewById<TextView>(R.id.alarm_item_to_address)
        internal var departTime = itemView.findViewById<TextView>(R.id.alarm_item_depart_time)
        internal var alarmTime = itemView.findViewById<TextView>(R.id.alarm_item_alarm_time)
        internal var alarmSwitch = itemView.findViewById<Switch>(R.id.alarm_item_switch)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemCLick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemCLick(alarm: Alarm)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}