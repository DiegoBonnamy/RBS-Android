package com.bonnamy.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.adapters.SegmentsAdapter
import com.bonnamy.dashboard.bo.Segment
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SegmentsListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SEGMENT_TYPE_ID = "segmentTypeId"
    }

    lateinit var segmentsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segments_list)

        val segmentTypeId = intent.getIntExtra(EXTRA_SEGMENT_TYPE_ID,1)

        if(segmentTypeId == 1){
            setTitle("Ascensions")
        }
        else{
            setTitle("Sprints")
        }

        this.segmentsRecyclerView = findViewById(R.id.bloc_segmentsList)
        segmentsRecyclerView.setHasFixedSize(true)
        segmentsRecyclerView.layoutManager = GridLayoutManager(this, 2)

        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        if(ReseauHelper.estConnecte(this)){
            CoroutineScope(Dispatchers.IO).launch {
                val segments: Array<Segment> = if(segmentTypeId == 1){
                    service.getSegmentsAscensions()
                } else{
                    service.getSegmentsSprints()
                }

                withContext(Dispatchers.Main) {

                    // Gestion temps null
                    for(s in segments){
                        if(s.temps1.temps == null){ s.temps1.temps = "1000" }
                        if(s.temps2.temps == null){ s.temps2.temps = "1000" }
                        if(s.temps3.temps == null){ s.temps3.temps = "1000" }
                        if(s.temps4.temps == null){ s.temps4.temps = "1000" }
                    }

                    val segmentsAdapter = SegmentsAdapter(segments.toMutableList())
                    segmentsRecyclerView.adapter = segmentsAdapter
                }
            }
        }
    }
}