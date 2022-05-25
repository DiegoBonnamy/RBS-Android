package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonnamy.dashboard.adapters.RecordsAdapter
import com.bonnamy.dashboard.viewmodels.RecordsViewModel
import com.bonnamy.dashboard.databinding.ActivityRecordsListBinding

class RecordsListActivity : AppCompatActivity() {

    private lateinit var activityRecordsListBinding: ActivityRecordsListBinding
    private lateinit var recordsViewModel: RecordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityRecordsListBinding = DataBindingUtil.setContentView(this, R.layout.activity_records_list)

        // à ajouter pour de meilleures performances :
        activityRecordsListBinding.blocRecordsList.setHasFixedSize(true)

        // layout manager, décrivant comment les items sont disposés :
        activityRecordsListBinding.blocRecordsList.layoutManager = LinearLayoutManager(this)

        // view model :
        recordsViewModel = ViewModelProvider(this).get(RecordsViewModel::class.java)

        // adapter :
        val recordsAdapter = RecordsAdapter(recordsViewModel)
        activityRecordsListBinding.blocRecordsList.adapter = recordsAdapter

        // observe :
        recordsViewModel.liveDataRecordsList.observe(this, {
            recordsAdapter.submitList(it)
        })

        // chargement :
        recordsViewModel.loadRecords(this)
    }
}