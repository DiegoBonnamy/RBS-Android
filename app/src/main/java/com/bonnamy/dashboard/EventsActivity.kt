package com.bonnamy.dashboard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        setTitle("Events")

        val txtNextEventName: TextView = findViewById(R.id.txt_nextEventName)
        val txtNextEventSport: TextView = findViewById(R.id.txt_nextEventSport)
        val txtNextEventDate: TextView = findViewById(R.id.txt_nextEventDate)

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
                val events: Array<Event> = service.getEvents()

                withContext(Dispatchers.Main) {
                    // Adapter
                    val eventsAdapter = EventsAdapter(events.toMutableList())
                    recyclerView.adapter = eventsAdapter

                    /* NEXT EVENT */
                    var nextEvent: Event = events[0]
                    for (event in events) {
                        nextEvent = event
                        if (event.acces == "Ouvert") {
                            break
                        }
                    }
                    txtNextEventName.text = nextEvent.nom
                    txtNextEventSport.text = nextEvent.sport
                    txtNextEventDate.text = nextEvent.date_1 + " " + nextEvent.date_2

                    // Next Event click
                    val blocNextEvent: ConstraintLayout = findViewById(R.id.bloc_nextEvent)
                    blocNextEvent.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(nextEvent.lien))
                        startActivity(intent)
                    }
                }
            }
        }
    }
}