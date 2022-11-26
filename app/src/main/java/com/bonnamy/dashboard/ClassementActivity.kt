package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bonnamy.dashboard.adapters.ClassementAdapter
import com.bonnamy.dashboard.bo.Classement
import com.bonnamy.dashboard.databinding.ActivityClassementBinding
import com.bonnamy.dashboard.viewmodels.ClassementViewModel
import com.google.android.material.tabs.TabLayout

class ClassementActivity : AppCompatActivity() {

    private lateinit var activityClassementBinding: ActivityClassementBinding
    private lateinit var classementViewModel: ClassementViewModel
    private lateinit var classementAdapter: ClassementAdapter
    private var clsmt: List<Classement> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classement)

        title = "Classements"

        activityClassementBinding = DataBindingUtil.setContentView(this, R.layout.activity_classement)
        classementViewModel = ViewModelProvider(this).get(ClassementViewModel::class.java)
        classementViewModel.loadClsmt(this)

        val pager = activityClassementBinding.classementViewpager
        classementAdapter = ClassementAdapter(supportFragmentManager, clsmt)
        pager.adapter = classementAdapter

        val tabs: TabLayout = activityClassementBinding.classementTabs
        tabs.setupWithViewPager(pager)
        tabs.tabMode = TabLayout.MODE_SCROLLABLE

        configureViewPager()
    }

    private fun configureViewPager() {
        classementViewModel.clsmtData.observe(this) {
            clsmt = it
            classementAdapter.updateClsmt(it)
            activityClassementBinding.classementLoading.visibility = View.GONE
        }
    }
}