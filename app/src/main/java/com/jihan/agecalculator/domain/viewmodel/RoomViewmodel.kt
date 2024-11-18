package com.jihan.agecalculator.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jihan.agecalculator.domain.room.AgeDao
import com.jihan.agecalculator.domain.room.AgeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewmodel @Inject constructor(private val ageDao: AgeDao) : ViewModel() {

    private val _ageList = MutableStateFlow<List<AgeEntity>>(emptyList())
    val ageList = _ageList.asStateFlow()

    private val _age = MutableStateFlow<AgeEntity?>(null)
    val age = _age.asStateFlow()



    init {
        observeAges()
    }

    fun insertAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            ageDao.insertAge(ageEntity)
        }
    }




    fun deleteAge(ageEntity: AgeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            ageDao.deleteAge(ageEntity)
        }
    }


    fun getAgeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _age.value = ageDao.getAgeById(id)
        }
    }


    fun searchAges(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ageList.value =ageDao.searchAges(query)
        }
    }


    private fun observeAges() {
        viewModelScope.launch {
           ageDao.getAge().collect { ages ->
                _ageList.value = ages
            }
        }
    }


}