package com.jihan.agecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jihan.agecalculator.domain.viewmodel.RoomViewmodel
import com.jihan.agecalculator.domain.viewmodel.ThemeViewModel
import com.jihan.agecalculator.presentation.screens.DetailScreen
import com.jihan.agecalculator.presentation.screens.MainScreen
import com.jihan.agecalculator.presentation.screens.destination.Destination
import com.jihan.agecalculator.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        setContent {


            val themeViewModel = hiltViewModel<ThemeViewModel>()
            val darkTheme by themeViewModel.isDark.collectAsStateWithLifecycle()

            AppTheme(darkTheme = darkTheme) {
                MainApp(themeViewModel)
            }
        }

    }


    @Composable
    private fun MainApp(themeViewModel: ThemeViewModel) {

        val navController = rememberNavController()
        val roomViewmodel = hiltViewModel<RoomViewmodel>()

        NavHost(navController, Destination.Main) {

            composable<Destination.Main>(popEnterTransition = {
                slideInHorizontally { -it }
            }) {
                MainScreen(navController, roomViewmodel, themeViewModel)
            }

            composable<Destination.Detail>(popExitTransition = {
                slideOutVertically { -it }
            }) {
                val id = it.toRoute<Destination.Detail>().id
                DetailScreen(id) {entity->
                 roomViewmodel.insertAge(entity)
                }
            }

        }


    }
}








