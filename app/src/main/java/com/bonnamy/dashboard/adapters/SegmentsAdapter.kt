package com.bonnamy.dashboard.adapters

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bonnamy.dashboard.R
import com.bonnamy.dashboard.bo.Segment
import com.squareup.picasso.Picasso
import kotlin.math.round
import android.widget.RelativeLayout
import androidx.core.view.marginTop
import com.bonnamy.dashboard.SegmentDetailsActivity
import com.bonnamy.dashboard.SettingsActivity


class SegmentsAdapter(private var segmentsList: MutableList<Segment>) :
    RecyclerView.Adapter<SegmentsAdapter.SegmentViewHolder>()
{
    // Creation de la liste
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SegmentsAdapter.SegmentViewHolder {
        val viewSegment = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_segment, parent, false)
        return SegmentViewHolder(viewSegment)
    }

    // Remplissage de la liste
    override fun onBindViewHolder(holder: SegmentViewHolder, position: Int) {

        // Nom du segment
        holder.txtSegmentName.text = segmentsList[position].nom

        // Catégorie du segment
        if(segmentsList[position].type == "Ascension"){
            val cat = when(round(segmentsList[position].distance.toInt() * segmentsList[position].pente.toDouble()).toInt()){
                in 0..2300 -> "4"
                in 2300..2600 -> "3"
                in 2600..2900 -> "2"
                in 2900..3200 -> "1"
                else -> "HC"
            }
            holder.txtSegmentCat.text = cat

            when(cat){
                "4" -> holder.imgCat.setBackgroundResource(R.drawable.icon_cat_4)
                "3" -> holder.imgCat.setBackgroundResource(R.drawable.icon_cat_3)
                "2" -> holder.imgCat.setBackgroundResource(R.drawable.icon_cat_2)
                "1" -> holder.imgCat.setBackgroundResource(R.drawable.icon_cat_1)
                else -> holder.imgCat.setBackgroundResource(R.drawable.icon_cat_hc)
            }
        }
        else{
            val cat = when(round(segmentsList[position].distance.toInt() * segmentsList[position].pente.toDouble()).toInt()){
                in 0..300 -> "3"
                in 300..600 -> "2"
                else -> "1"
            }
            holder.txtSegmentCat.text = cat

            when(cat){
                "3" -> holder.imgCat.setBackgroundResource(R.drawable.icon_sprint_3)
                "2" -> holder.imgCat.setBackgroundResource(R.drawable.icon_sprint_2)
                else -> holder.imgCat.setBackgroundResource(R.drawable.icon_sprint_1)
            }

            val layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            holder.txtSegmentCat.layoutParams = layoutParams
        }


        // Photo du leader
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post(Runnable {
            Picasso.get()
                .load(segmentsList[position].leader.photo)
                .into(holder.imgLeader)
        })

        // Temps du leader
        var min = (segmentsList[position].leader.temps.toInt()/60).toString()
        if(min.toInt() < 10){ min = "0" + min }
        var sec = (segmentsList[position].leader.temps.toInt()%60).toString()
        if(sec.toInt() < 10){ sec = "0" + sec }
        holder.txtLeaderTime.text =  min + ":" + sec
    }

    // Count de la list
    override fun getItemCount(): Int {
        return segmentsList.size
    }

    // View Holder
    inner class SegmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtSegmentName: TextView = itemView.findViewById(R.id.txt_segmentName)
        val txtSegmentCat: TextView = itemView.findViewById(R.id.txt_segmentCat)
        val imgLeader: ImageView = itemView.findViewById(R.id.img_leader)
        val txtLeaderTime: TextView = itemView.findViewById(R.id.txt_leaderTime)
        val imgCat: ImageView = itemView.findViewById(R.id.img_iconCat)

        init {
            itemView.setOnClickListener{
                val segment = segmentsList[adapterPosition]
                val intent = Intent(itemView.context, SegmentDetailsActivity::class.java)
                intent.putExtra(SegmentDetailsActivity.EXTRA_SEGMENT, segment)
                itemView.context.startActivity(intent)
            }
        }
    }

}