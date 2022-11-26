package com.bonnamy.dashboard.bo

import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bonnamy.dashboard.R
import kotlinx.parcelize.Parcelize

@BindingAdapter("imageResource")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@Parcelize
data class AthletePosition(
    val id: String,
    val picture: String,
    val color: String,
    val classement_semaine: String,
    val points_semaine: String,
    val evolution_points: String,
    val details_semaine: String,
    val type_semaine: String,
    val classement_general: String,
    val points_general: String,
    val gain_general: String,
    val forme: String
) : Parcelable {

    fun pointsFormat(pts: String) = if(pts.toFloat() > 1) "$pts pts" else "$pts pt"

    fun classementFormat(clsmt: String): String {
        return when(clsmt.toInt()) {
            1 ->
                clsmt + "er"
            else ->
                clsmt + "ème"
        }
    }

    fun detailsSemaineFormat(sport: Int): String {
        val details = details_semaine.split(";")
        return details[sport - 1] + " km"
    }

    fun detailsSemaineVisibility(sport: Int): Int {
        val details = details_semaine.split(";")
        return if(details[sport - 1].toFloat() > 0) View.VISIBLE else View.GONE
    }

    fun gainGeneralFormat() = if(gain_general.toInt() > 1) "+ $gain_general pts" else "+ $gain_general pt"

    fun formeFormat() = "$forme%"

    fun medalIcon(clsmt: String): Int {
        return when(clsmt.toInt()){
            1 ->  R.drawable.icon_1st
            2 -> R.drawable.icon_2nd
            3 -> R.drawable.icon_3rd
            else -> R.drawable.icon_empty
        }
    }

    fun formeIcon(): Int {
        return when {
            forme.toFloat() >= 90 -> R.drawable.icon_fire
            forme.toFloat() >= 75 -> R.drawable.icon_green
            forme.toFloat() >= 50 -> R.drawable.icon_orange
            else -> R.drawable.icon_red
        }
    }

    fun evolutionPointsIcon(): Int {
        return when(evolution_points) {
            "up" -> R.drawable.icon_up
            "down" -> R.drawable.icon_down
            else -> R.drawable.icon_empty
        }
    }
}