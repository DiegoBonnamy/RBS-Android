package com.bonnamy.dashboard.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonnamy.dashboard.bo.Record
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordsViewModel : ViewModel() {

    val liveDataRecordsList = MutableLiveData<List<Record>>()

    fun loadRecords(context: Context)
    {
        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        if(ReseauHelper.estConnecte(context)) {
            CoroutineScope(Dispatchers.IO).launch {

                val recordsList = service.getRecords()
                liveDataRecordsList.postValue(recordsList)
            }
        }
    }
}