package com.jihan.agecalculator.domain.viewmodel


import androidx.lifecycle.ViewModel
import com.jihan.agecalculator.domain.room.AgeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewmodel @Inject constructor() : ViewModel() {



    private val _ageEntity = MutableStateFlow<AgeEntity?>(null)
    val ageEntity = _ageEntity.asStateFlow()


    fun setAgeEntity(ageEntity: AgeEntity) {
        _ageEntity.value = ageEntity
    }


}