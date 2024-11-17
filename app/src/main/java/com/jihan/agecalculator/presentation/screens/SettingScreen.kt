package com.jihan.agecalculator.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jihan.agecalculator.domain.viewmodel.ThemeViewModel
import com.jihan.agecalculator.presentation.component.ThemeSwitcher

@Composable
fun SettingScreen(themeViewModel: ThemeViewModel = hiltViewModel()) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isDark by themeViewModel.isDark.collectAsStateWithLifecycle()


        val text = if (isDark) "Dark Theme" else "Light Theme"

        AnimatedContent(text, label = "") {
            Text(it, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
        }

        Spacer(Modifier.height(25.dp))
        ThemeSwitcher(
            darkTheme = isDark, size = 70.dp
        ) {
            themeViewModel.toggleTheme()
        }


    }
}
