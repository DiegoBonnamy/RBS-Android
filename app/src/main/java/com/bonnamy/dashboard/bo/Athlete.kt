package com.bonnamy.dashboard.bo

import android.graphics.Color
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import kotlin.math.round

@BindingAdapter("urlImageAthlete")
fun bindUrlImageAthlete(view: ImageView, imageUrl: String?) {
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

@BindingAdapter("backgroundColor")
fun ViewGroup.setBackground(backgroundColor: String) {
    val color: Int = Color.parseColor(backgroundColor)
    setBackgroundColor(color)
}

data class Athlete (
    val id: String,
    val prenom: String,
    val couleur: String,
    val photo: String,
    val dureeTotale: Float,
    val distanceTotale: Float,
    val deniveleTotal: Float,
    val distanceCyclisme: Float,
    val distanceRunning: Float,
    val objectifCyclisme: String,
    val objectifRunning: String
){
    fun getDuree(): String{
        return round(dureeTotale).toInt().toString() + " h"
    }

    fun getDistance(): String{
        return round(distanceTotale).toInt().toString() + " km"
    }

    fun getDenivele(): String{
        return round(deniveleTotal).toInt().toString() + " m"
    }
}