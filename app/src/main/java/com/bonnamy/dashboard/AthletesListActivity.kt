package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bonnamy.dashboard.adapters.AthleteAdapter
import com.bonnamy.dashboard.databinding.ActivityAthletesListBinding
import com.bonnamy.dashboard.viewmodels.AthletesViewModel

class AthletesListActivity : AppCompatActivity() {

    private lateinit var activityAthletesListBinding: ActivityAthletesListBinding
    private lateinit var athletesViewModel: AthletesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_athletes_list)

        activityAthletesListBinding = DataBindingUtil.setContentView(this, R.layout.activity_athletes_list)

        activityAthletesListBinding.blocAthletesList.layoutManager = LinearLayoutManager(this)

        athletesViewModel = ViewModelProvider(this).get(AthletesViewModel::class.java)

        val athleteAdapter = AthleteAdapter(athletesViewModel)
        activityAthletesListBinding.blocAthletesList.adapter = athleteAdapter

        athletesViewModel.liveDataAthletesList.observe(this, {
            athleteAdapter.submitList(it)
        })

        athletesViewModel.loadAthletes(this)
    }
}