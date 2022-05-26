package com.bonnamy.dashboard.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bonnamy.dashboard.bo.Athlete

class AthleteDiffCallback : DiffUtil.ItemCallback<Athlete>() {

    override fun areItemsTheSame(oldItem: Athlete, newItem: Athlete): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Athlete, newItem: Athlete): Boolean {
        return oldItem == newItem
    }
}