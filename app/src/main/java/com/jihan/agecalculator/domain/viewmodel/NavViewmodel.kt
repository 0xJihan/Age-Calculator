package com.jihan.agecalculator.domain.viewmodel

import androidx.lifecycle.ViewModel
import cafe.adriel.voyager.navigator.Navigator
import javax.inject.Inject


class NavViewmodel @Inject constructor() : ViewModel() {

    lateinit var navigator: Navigator

    fun updateNavigator(navigator: Navigator) {
        this.navigator = navigator
    }

}