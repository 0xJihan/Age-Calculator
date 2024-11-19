package com.jihan.agecalculator

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jihan.agecalculator.domain.utils.worker.scheduleBirthdayCheck
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
        RequestNotificationPermission()


            val themeViewModel = hiltViewModel<ThemeViewModel>()
            val darkTheme by themeViewModel.isDark.collectAsStateWithLifecycle()

            AppTheme(darkTheme = darkTheme) {
                MainApp(themeViewModel)
            }
        }
        scheduleBirthdayCheck(this)

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
                DetailScreen(id) { entity ->
                    roomViewmodel.insertAge(entity)
                }
            }

        }


    }


}

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current

    // Launcher to request permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* Do nothing, this is just for permission */ }
    )

    // Check and request permission
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}



