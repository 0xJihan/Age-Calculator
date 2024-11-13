package com.jihan.agecalculator.domain.model

import android.net.Uri

data class InputResponse(
    val name: String,
    val description: String,
    var imageUri: Uri? = null
)
