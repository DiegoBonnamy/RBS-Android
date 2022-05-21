package com.bonnamy.dashboard.adapters

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.R
import com.bonnamy.dashboard.bo.Event
import java.lang.Math.abs

class EventsAdapter(private var listeEvents: MutableList<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>()
{
    // Creer la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val viewCourse = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(viewCourse)
    }

    // Rempli la liste
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.txtEventName.text = listeEvents[position].nom
        holder.txtEventDate.text = listeEvents[position].date_1 + "\n" + listeEvents[position].date_2

        var dateDiff = listeEvents[position].dateDiff
        var eventClose = false
        if(dateDiff.toInt() > 0){
            dateDiff = "+ " + dateDiff
            eventClose = true
        }
        else{
            dateDiff = "- " + abs(dateDiff.toInt())
        }
        holder.txtEventDateDiff.text = "J " + dateDiff

        if(eventClose){
            holder.txtEventDate.setBackgroundColor(Color.parseColor("#5c5c5c"))
        }
        else {
            if (listeEvents[position].sport == "Cyclisme") {
                holder.txtEventDate.setBackgroundColor(Color.parseColor("#036bfc"))
            } else {
                holder.txtEventDate.setBackgroundColor(Color.parseColor("#fc0303"))
            }
        }
    }

    override fun getItemCount(): Int {
        return listeEvents.size;
    }

    // View Holder
    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtEventDate: TextView = itemView.findViewById(R.id.txt_eventDate)
        val txtEventName: TextView = itemView.findViewById(R.id.txt_eventName)
        val txtEventDateDiff: TextView = itemView.findViewById(R.id.txt_eventDateDiff)

        init {
            itemView.setOnClickListener{
                // Event cliqué
                val event = listeEvents[adapterPosition]

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.lien))
                itemView.context.startActivity(intent)
            }
        }
    }
}