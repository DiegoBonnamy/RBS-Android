package com.bonnamy.dashboard.ws

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rbs.diegobonnamy.fr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}