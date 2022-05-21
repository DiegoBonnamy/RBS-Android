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