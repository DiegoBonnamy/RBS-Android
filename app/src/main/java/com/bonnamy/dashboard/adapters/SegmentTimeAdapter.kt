package com.bonnamy.dashboard.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.R
import com.bonnamy.dashboard.bo.SegmentTime
import com.squareup.picasso.Picasso

class SegmentTimeAdapter(private var segmentTimeList: MutableList<SegmentTime>) :
    RecyclerView.Adapter<SegmentTimeAdapter.SegmentTimeViewHolder>()
{
    // Creation de la liste
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SegmentTimeAdapter.SegmentTimeViewHolder {
        val viewSegmentTime = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_segment_time, parent, false)
        return SegmentTimeViewHolder(viewSegmentTime)
    }

    // Remplissage de la liste
    override fun onBindViewHolder(holder: SegmentTimeAdapter.SegmentTimeViewHolder, position: Int) {

        // Photo de l'athlete
        Handler(Looper.getMainLooper()).post(Runnable {
            Picasso.get()
                .load(segmentTimeList[position].picture)
                .into(holder.imgAthlete)
        })

        // Nom de l'athlete
        holder.txtAthleteName.text = segmentTimeList[position].name

        // Temps de l'athlete
        if(segmentTimeList[position].time != 1000){
            if (position == 0){
                var min = (segmentTimeList[position].time/60).toString()
                if(min.toInt() < 10){ min = "0" + min }
                var sec = (segmentTimeList[position].time%60).toString()
                if(sec.toInt() < 10){ sec = "0" + sec }
                holder.txtAthleteTime.text =  min + ":" + sec
            }
            else{
                val leaderTime = segmentTimeList[0].time
                val diff = segmentTimeList[position].time - leaderTime

                var min = (diff/60).toString()
                if(min.toInt() < 10){ min = "0" + min }
                var sec = (diff%60).toString()
                if(sec.toInt() < 10){ sec = "0" + sec }
                holder.txtAthleteTime.text =  "+ " + min + ":" + sec
            }
        }
        else{
            holder.txtAthleteTime.text =  "-"
        }

        // Vitesse de l'athlete
        if(segmentTimeList[position].time != 1000) {
            holder.txtAthleteSpeed.text = segmentTimeList[position].speed.toString() + " km/h"
        }
        else{
            holder.txtAthleteSpeed.text = "-"
        }
    }

    // Count de la list
    override fun getItemCount(): Int {
        return segmentTimeList.size
    }

    // View Holder
    inner class SegmentTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgAthlete: ImageView = itemView.findViewById(R.id.img_athlete)
        val txtAthleteName: TextView = itemView.findViewById(R.id.txt_athleteName)
        val txtAthleteTime: TextView = itemView.findViewById(R.id.txt_athleteTime)
        val txtAthleteSpeed: TextView = itemView.findViewById(R.id.txt_athleteSpeed)

        init {

        }
    }
}