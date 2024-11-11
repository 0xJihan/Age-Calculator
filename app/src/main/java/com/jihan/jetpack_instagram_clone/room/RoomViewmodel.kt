package com.jihan.jetpack_instagram_clone.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        getAllAges()
    }

    fun insertAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.ageDao().insertAge(ageEntity)
            getAllAges()
        }
    }

    fun deleteAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.ageDao().deleteAge(ageEntity)
            getAllAges()
        }
    }

    private fun getAllAges() {
        viewModelScope.launch(Dispatchers.IO) {
            _ageList.value = appDatabase.ageDao().getAge()
        }
    }


    fun searchAges(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ageList.value = appDatabase.ageDao().searchAges(query)
        }
    }


}