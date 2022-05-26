package com.bonnamy.dashboard.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonnamy.dashboard.ProfilActivity
import com.bonnamy.dashboard.bo.Athlete
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AthletesViewModel : ViewModel() {

    val liveDataAthletesList = MutableLiveData<List<Athlete>>()

    fun clicAthlete(athlete: Athlete, context: Context)
    {
        val intent = Intent(context, ProfilActivity::class.java)
        intent.putExtra(ProfilActivity.EXTRA_ATHLETE_ID, athlete.id.toInt())
        context.startActivity(intent)
    }

    fun loadAthletes(context: Context){
        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        if(ReseauHelper.estConnecte(context)){
            CoroutineScope(Dispatchers.IO).launch {
                val athletesList = service.getAthletes()
                liveDataAthletesList.postValue(athletesList)
            }
        }
    }
}