package com.jihan.agecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jihan.agecalculator.domain.viewmodel.RoomViewmodel
import com.jihan.agecalculator.presentation.screens.DetailScreen
import com.jihan.agecalculator.presentation.screens.HomeScreen
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
            AppTheme {
                MainApp()
            }
        }

    }


    @Composable
    private fun MainApp() {

        val navController = rememberNavController()
        val roomViewmodel = hiltViewModel<RoomViewmodel>()

        NavHost(navController, Destination.Main) {

            composable<Destination.Main> {
                MainScreen(navController,roomViewmodel)
            }

            composable<Destination.Detail> {
                val id = it.toRoute<Destination.Detail>().id
                DetailScreen(id) {entity->
                 roomViewmodel.insertAge(entity)
                }
            }

        }


    }
}








