package com.bonnamy.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.bo.Athlete
import com.bonnamy.dashboard.databinding.ItemAthleteBinding
import com.bonnamy.dashboard.viewmodels.AthletesViewModel

class AthleteAdapter(private val athletesViewModel: AthletesViewModel) :
    ListAdapter<Athlete, AthleteAdapter.AthleteViewHolder>(AthleteDiffCallback())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteViewHolder
    {
        val itemAthleteBinding = ItemAthleteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return AthleteViewHolder(itemAthleteBinding)
    }

    override fun onBindViewHolder(holder: AthleteViewHolder, position: Int) = holder.bind(getItem(position))

    // ViewHolder :
    inner class AthleteViewHolder(private val itemAthleteBinding: ItemAthleteBinding) :
        RecyclerView.ViewHolder(itemAthleteBinding.root)
    {
        fun bind(athlete: Athlete)
        {
            itemAthleteBinding.athlete = athlete
            itemAthleteBinding.viewModel = athletesViewModel
            itemAthleteBinding.executePendingBindings()
        }
    }
}