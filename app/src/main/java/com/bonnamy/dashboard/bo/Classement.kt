package com.bonnamy.dashboard.bo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Classement(
    val semaine: String,
    val date: String,
    val athletes: List<AthletePosition>
) : Parcelable