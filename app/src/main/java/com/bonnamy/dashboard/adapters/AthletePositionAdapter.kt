package com.bonnamy.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.bo.AthletePosition
import com.bonnamy.dashboard.databinding.ItemPositionBinding

class AthletePositionAdapter :
    ListAdapter<AthletePosition, AthletePositionAdapter.PositionViewHolder>(AthletePositionDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val itemPositionBinding = ItemPositionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PositionViewHolder(itemPositionBinding)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) = holder.bind(getItem(position))

    inner class PositionViewHolder(private val itemPositionBinding: ItemPositionBinding) :
        RecyclerView.ViewHolder(itemPositionBinding.root)
    {
        fun bind(position: AthletePosition){
            itemPositionBinding.position = position
            itemPositionBinding.executePendingBindings()
        }
    }
}