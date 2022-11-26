package com.bonnamy.dashboard.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonnamy.dashboard.bo.Classement
import com.bonnamy.dashboard.helpers.ReseauHelper
import com.bonnamy.dashboard.ws.RetrofitSingleton
import com.bonnamy.dashboard.ws.WSInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassementViewModel: ViewModel() {

    val clsmtData = MutableLiveData<List<Classement>>()

    fun loadClsmt(context: Context) {
        val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
        if(ReseauHelper.estConnecte(context)) {
            CoroutineScope(Dispatchers.IO).launch {

                val clsmtList = service.getClassement()
                clsmtData.postValue(clsmtList)
            }
        }
    }
}