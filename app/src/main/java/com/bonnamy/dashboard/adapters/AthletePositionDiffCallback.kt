package com.bonnamy.dashboard.adapters

import androidx.recyclerview.widget.DiffUtil
import com.bonnamy.dashboard.bo.AthletePosition

class AthletePositionDiffCallback: DiffUtil.ItemCallback<AthletePosition>() {

    override fun areItemsTheSame(oldItem: AthletePosition, newItem: AthletePosition): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AthletePosition, newItem: AthletePosition): Boolean {
        return oldItem == newItem
    }
}