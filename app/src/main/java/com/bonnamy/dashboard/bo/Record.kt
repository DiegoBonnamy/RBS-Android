package com.bonnamy.dashboard.bo

import android.widget.ImageView
import com.squareup.picasso.Picasso
import androidx.databinding.BindingAdapter

@BindingAdapter("urlImage")
fun bindUrlImage(view: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(view)
    } else {
        view.setImageBitmap(null)
    }
}

data class Record(
    val id: String,
    val type_record: String,
    val athletes: List<AthleteTime>
){
    fun getAthleteTime(position: Int) : AthleteTime{
        return athletes.sortedBy { it.getTime() }[position]
    }

    fun getAthleteTimeFormat(position: Int) : String{
        val time: Int = getAthleteTime(position).temps.toInt()
        return if(time != 0){
            if(position == 0){
                if(time < 3600){
                    var min = (time/60).toString()
                    if(min.toInt() < 10){ min = "0" + min }
                    var sec = (time%60).toString()
                    if(sec.toInt() < 10){ sec = "0" + sec }

                    min + ":" + sec
                }
                else{
                    var h = (time/60/60).toString()
                    if(h.toInt() < 10){ h = "0" + h }
                    var min = ((time/60)-(time/60/60)*60).toString()
                    if(min.toInt() < 10){ min = "0" + min }
                    var sec = (time%60).toString()
                    if(sec.toInt() < 10){ sec = "0" + sec }

                    h+ ":" + min + ":" + sec
                }
            }
            else{
                val leaderTime = getAthleteTime(0).getTime()
                var diff = time - leaderTime

                var min = (diff/60).toString()
                if(min.toInt() < 10){ min = "0" + min }
                var sec = (diff%60).toString()
                if(sec.toInt() < 10){ sec = "0" + sec }

                "+ " + min + ":" + sec
            }
        } else{
            "-"
        }
    }
}