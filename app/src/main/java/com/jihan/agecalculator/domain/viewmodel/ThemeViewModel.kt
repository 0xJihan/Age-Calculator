package com.jihan.agecalculator.domain.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jihan.agecalculator.domain.utils.ThemeChanger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    private val _isDark = MutableStateFlow(ThemeChanger.getCurrentTheme(context))
    val isDark: StateFlow<Boolean> get() = _isDark

    fun toggleTheme() {
        val newTheme = !_isDark.value
        _isDark.value = newTheme
        ThemeChanger.setTheme(context, newTheme)
    }
}
