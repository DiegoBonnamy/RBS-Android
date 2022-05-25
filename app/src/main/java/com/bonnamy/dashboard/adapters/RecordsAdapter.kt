package com.bonnamy.dashboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.bo.Record
import com.bonnamy.dashboard.databinding.ItemRecordBinding
import com.bonnamy.dashboard.viewmodels.RecordsViewModel

class RecordsAdapter(private val recordsViewModel: RecordsViewModel) :
    ListAdapter<Record, RecordsAdapter.RecordsViewHolder>(RecordDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsViewHolder
    {
        val itemRecordBinding = ItemRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return RecordsViewHolder(itemRecordBinding)
    }

    override fun onBindViewHolder(holder: RecordsViewHolder, position: Int) = holder.bind(getItem(position))

    // ViewHolder :
    inner class RecordsViewHolder(private val itemRecordBinding: ItemRecordBinding) :
        RecyclerView.ViewHolder(itemRecordBinding.root)
    {
        fun bind(record: Record)
        {
            itemRecordBinding.record = record
            itemRecordBinding.viewModel = recordsViewModel
            itemRecordBinding.executePendingBindings()
        }
    }
}