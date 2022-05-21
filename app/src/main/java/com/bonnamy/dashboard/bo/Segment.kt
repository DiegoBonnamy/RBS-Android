package com.bonnamy.dashboard.bo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Segment(
    val id: String,
    val id_strava: String,
    val nom: String,
    val distance: String,
    val pente: String,
    val secteur: String,
    val type: String,
    val leader: AthleteTime,
    val temps1: AthleteTime,
    val temps2: AthleteTime,
    val temps3: AthleteTime,
    val temps4: AthleteTime
) : Parcelable