package com.jihan.agecalculator.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.agecalculator.domain.room.AgeEntity
import com.jihan.agecalculator.domain.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewmodel @Inject constructor(private val appDatabase: AppDatabase) : ViewModel() {

    private val _ageList = MutableStateFlow<List<AgeEntity>>(emptyList())
    val ageList = _ageList.asStateFlow()



    init {
        observeAges()
    }

    fun insertAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.ageDao().insertAge(ageEntity)
        }
    }




    fun deleteAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.ageDao().deleteAge(ageEntity)
        }
    }




    fun searchAges(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ageList.value = appDatabase.ageDao().searchAges(query)
        }
    }


    private fun observeAges() {
        viewModelScope.launch {
            appDatabase.ageDao().getAge().collect { ages ->
                _ageList.value = ages
            }
        }
    }


}