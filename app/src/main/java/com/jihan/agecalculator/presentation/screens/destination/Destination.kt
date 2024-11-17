package com.jihan.agecalculator.presentation.screens.destination

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object Main : Destination

    @Serializable
    data class Detail(val id: Int) : Destination

}