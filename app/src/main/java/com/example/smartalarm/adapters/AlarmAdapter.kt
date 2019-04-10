package com.example.smartalarm.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarm.R
import com.example.smartalarm.data.Alarm
import kotlinx.android.synthetic.main.alarm_item.view.*

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmHolder>(DIFF_CALLBACK) {
    private var listener: OnItemClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm): Boolean {
                return oldItem.day == newItem.day && oldItem.alarmTime == newItem.alarmTime
                        && oldItem.departTime == newItem.departTime && oldItem.fromAddress == newItem.fromAddress
                        && oldItem.toAddress == newItem.toAddress && oldItem.isActive == newItem.isActive
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_item, parent, false)
        return AlarmHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {
        val currentAlarm = getItem(position)
        holder.day.text = currentAlarm.day
        holder.fromAddress.text = "From: ${currentAlarm.fromAddress}"
        holder.toAddress.text = "To: ${currentAlarm.toAddress}"
        holder.departTime.text = "Departure time: ${currentAlarm.departTime}"
        holder.alarmTime.text = "Alarm time: ${currentAlarm.alarmTime}"
    }

    inner class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var day = itemView.findViewById<TextView>(R.id.dayTextView)
        internal var fromAddress = itemView.findViewById<TextView>(R.id.fromAddressTextView)
        internal var toAddress = itemView.findViewById<TextView>(R.id.toAddressTextView)
        internal var departTime = itemView.findViewById<TextView>(R.id.departTimeTextView)
        internal var alarmTime = itemView.findViewById<TextView>(R.id.alarmTimeTextView)

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