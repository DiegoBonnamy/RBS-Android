package com.bonnamy.dashboard.bo

data class Event(
    val id: String,
    val nom: String,
    val date_1: String,
    val date_2: String,
    val lien: String,
    val sport: String,
    val acces: String,
    val dateDiff: String
)