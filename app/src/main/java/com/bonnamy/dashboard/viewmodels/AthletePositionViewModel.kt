package com.bonnamy.dashboard.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonnamy.dashboard.bo.AthletePosition
import com.bonnamy.dashboard.bo.Classement

class AthletePositionViewModel: ViewModel() {

    val positionData = MutableLiveData<List<AthletePosition>>()

    fun loadPositions(clsmt: Classement?) {
        positionData.postValue(clsmt?.athletes ?: listOf())
    }
}