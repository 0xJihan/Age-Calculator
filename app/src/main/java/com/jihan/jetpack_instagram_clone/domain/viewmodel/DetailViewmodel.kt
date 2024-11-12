package com.jihan.jetpack_instagram_clone.domain.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.jihan.jetpack_instagram_clone.domain.room.AgeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewmodel @Inject constructor() :ViewModel() {


    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _ageEntity = MutableStateFlow<AgeEntity?>(null)
    val ageEntity = _ageEntity.asStateFlow()

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun setAgeEntity(ageEntity: AgeEntity) {
        _ageEntity.value = ageEntity
    }



}