package com.jihan.jetpack_instagram_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jihan.jetpack_instagram_clone.domain.utils.BottomTabIcon
import com.jihan.jetpack_instagram_clone.domain.utils.TabNavigationItem
import com.jihan.jetpack_instagram_clone.screens.HomeScreen
import com.jihan.jetpack_instagram_clone.screens.ListScreen
import com.jihan.jetpack_instagram_clone.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            AppTheme {
                MainApp()
            }
        }

    }

    @Composable
    private fun MainApp() {

        TabNavigator(HomeScreen()) { tabNavigator ->


            Scaffold(bottomBar = {
                BottomAppBar(Modifier.navigationBarsPadding()) {
                    TabNavigationItem(icon = BottomTabIcon.HOME, tab = HomeScreen())
                    TabNavigationItem(icon = BottomTabIcon.LIST, tab = ListScreen())
                }
            }) {
                Box(Modifier.padding(it)) {
                    AnimatedContent(
                        targetState = tabNavigator.current,
                        transitionSpec = { fadeIn(tween(500)) togetherWith fadeOut(tween(500)) },
                        label = ""
                    ) { tab ->
                        tab.Content()
                    }
                }
            }
        }
    }

}






