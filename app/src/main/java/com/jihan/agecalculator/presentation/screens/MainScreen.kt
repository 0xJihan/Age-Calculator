package com.jihan.agecalculator.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jihan.agecalculator.domain.utils.BottomItem
import com.jihan.agecalculator.domain.viewmodel.RoomViewmodel
import com.jihan.agecalculator.domain.viewmodel.ThemeViewModel
import com.jihan.agecalculator.presentation.screens.destination.Destination

@Composable
fun MainScreen(navController: NavController, roomViewmodel: RoomViewmodel,
               themeViewModel: ThemeViewModel= hiltViewModel()
) {
    val bottomNavList = listOf(
        BottomItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomItem("List", Icons.AutoMirrored.Filled.List, Icons.AutoMirrored.Outlined.List),
        BottomItem("Setting", Icons.Filled.Settings, Icons.Outlined.Settings),
    )

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(bottomBar = {
        BottomAppBar {
            bottomNavList.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                NavigationBarItem(icon = {
                    Icon(
                        if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null
                    )
                },
                    label = { Text(item.label) },
                    selected = isSelected,
                    onClick = { selectedIndex = index },
                    alwaysShowLabel = false
                )
            }
        }
    }) { paddingValues ->

        Box(Modifier.padding(paddingValues)) {
            AnimatedContent(targetState = selectedIndex, transitionSpec = {
                // Customizing the animation with slide and fade effects
                if (targetState > initialState) {
                    (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> -width } + fadeOut())
                } else {
                    (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                        slideOutHorizontally { width -> width } + fadeOut())
                }
            }, label = "") { index ->
                when (index) {
                    0 -> HomeScreen(roomViewmodel = roomViewmodel)
                    1 -> ListScreen(roomViewmodel) { id ->
                        navController.navigate(Destination.Detail(id))
                    }
                    2 -> SettingScreen(themeViewModel)
                }
            }
        }

    }

}

