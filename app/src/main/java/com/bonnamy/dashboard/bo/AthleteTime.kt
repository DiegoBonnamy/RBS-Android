package com.bonnamy.dashboard.bo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AthleteTime(
    val id: String,
    val prenom: String,
    val photo: String,
    var temps: String
) : Parcelable
{
    fun getTime(): Int{
        return if(this.temps.toInt() != 0){
            this.temps.toInt()
        } else{
            100_00
        }
    }
}