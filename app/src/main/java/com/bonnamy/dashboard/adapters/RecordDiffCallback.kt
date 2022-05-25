package com.bonnamy.dashboard.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bonnamy.dashboard.bo.Record

class RecordDiffCallback : DiffUtil.ItemCallback<Record>() {

    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}