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

class AlarmAdapter : ListAdapter<Alarm, AlarmAdapter.AlarmHolder>(DIFF_CALLBACK) {
    private var listener: OnItemClickListener? = null

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
        return AlarmHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {
        val currentAlarm = getItem(position)
        holder.fromAddress.text = "From: ${currentAlarm.fromAddress}"
        holder.toAddress.text = "To: ${currentAlarm.toAddress}"
        holder.departTime.text = "Departure time: ${currentAlarm.departTimeInMinutes}"
        holder.alarmTime.text = "Alarm time: ${currentAlarm.timeInMinutes}"
    }

    inner class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var fromAddress = itemView.findViewById<TextView>(R.id.fromAddressEditText)
        internal var toAddress = itemView.findViewById<TextView>(R.id.toAddressEditText)
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