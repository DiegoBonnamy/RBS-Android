package com.bonnamy.dashboard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.adapters.EventsAdapter
import com.bonnamy.dashboard.bo.Event
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    var events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        setTitle("Events")

        val switchCap: SwitchCompat = findViewById(R.id.switch_cap)
        val switchCyclos: SwitchCompat = findViewById(R.id.switch_cyclos)
        val switchCourses: SwitchCompat = findViewById(R.id.switch_courses)

        // Liste
        this.recyclerView = findViewById(R.id.bloc_eventsList)

        // A ajouter pour améliorer les performances
        recyclerView.setHasFixedSize(true)

        // Layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Call API
        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        if (ReseauHelper.estConnecte(this)) {
            CoroutineScope(Dispatchers.IO).launch {
                events = service.getNextEvents().toMutableList()

                withContext(Dispatchers.Main) {
                    // Adapter
                    val eventsAdapter = EventsAdapter(events)
                    recyclerView.adapter = eventsAdapter
                }
            }
        }

        // Switch
        switchCap.setOnClickListener {
            if (ReseauHelper.estConnecte(this)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val finalList: MutableList<Event> = mutableListOf()
                    finalList.addAll(events)

                    if(!switchCap.isChecked){
                        var i = 0
                        var nbRemove = 0
                        events.forEach {
                            if(it.sport == "Running"){
                                finalList.removeAt(i - nbRemove)
                                nbRemove++
                            }
                            i++
                        }

                        events.clear()
                        events.addAll(finalList)
                    }
                    else{
                        finalList.clear()
                        finalList.addAll(service.getNextEvents().toMutableList())
                        var i = 0
                        finalList.forEach {
                            if(it.sport == "Running"){
                                events.add(it)
                            }
                            i++
                        }
                    }

                    events.sortByDescending  { it.dateDiff.toInt() }

                    withContext(Dispatchers.Main) {
                        // Adapter
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        switchCyclos.setOnClickListener {
            if (ReseauHelper.estConnecte(this)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val finalList: MutableList<Event> = mutableListOf()
                    finalList.addAll(events)

                    if(!switchCyclos.isChecked){
                        var i = 0
                        var nbRemove = 0
                        events.forEach {
                            if(it.sport == "Cyclisme" && it.type == "Cyclosportive"){
                                finalList.removeAt(i - nbRemove)
                                nbRemove++
                            }
                            i++
                        }

                        events.clear()
                        events.addAll(finalList)
                    }
                    else{
                        finalList.clear()
                        finalList.addAll(service.getNextEvents().toMutableList())
                        var i = 0
                        finalList.forEach {
                            if(it.sport == "Cyclisme" && it.type == "Cyclosportive"){
                                events.add(it)
                            }
                            i++
                        }
                    }

                    events.sortByDescending  { it.dateDiff.toInt() }

                    withContext(Dispatchers.Main) {
                        // Adapter
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        switchCourses.setOnClickListener {
            if (ReseauHelper.estConnecte(this)) {
                CoroutineScope(Dispatchers.IO).launch {
                    val finalList: MutableList<Event> = mutableListOf()
                    finalList.addAll(events)

                    if(!switchCourses.isChecked){
                        var i = 0
                        var nbRemove = 0
                        events.forEach {
                            if(it.sport == "Cyclisme" && it.type == "Course"){
                                finalList.removeAt(i - nbRemove)
                                nbRemove++
                            }
                            i++
                        }

                        events.clear()
                        events.addAll(finalList)
                    }
                    else{
                        finalList.clear()
                        finalList.addAll(service.getNextEvents().toMutableList())
                        var i = 0
                        finalList.forEach {
                            if(it.sport == "Cyclisme" && it.type == "Course"){
                                events.add(it)
                            }
                            i++
                        }
                    }

                    events.sortByDescending  { it.dateDiff.toInt() }

                    withContext(Dispatchers.Main) {
                        // Adapter
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}