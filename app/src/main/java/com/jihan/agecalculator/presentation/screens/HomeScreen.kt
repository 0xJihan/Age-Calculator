package com.jihan.agecalculator.presentation.screens

import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jihan.agecalculator.domain.room.AgeEntity
import com.jihan.agecalculator.domain.utils.BottomItem
import com.jihan.agecalculator.domain.utils.calculateAgeDetails
import com.jihan.agecalculator.domain.utils.saveImage
import com.jihan.agecalculator.domain.viewmodel.RoomViewmodel
import com.jihan.agecalculator.presentation.component.InputDialog
import com.jihan.agecalculator.presentation.screens.destination.Destination
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun MainScreen(navController: NavController,roomViewmodel: RoomViewmodel) {
    val bottomNavList = listOf(
        BottomItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomItem("List", Icons.AutoMirrored.Filled.List, Icons.AutoMirrored.Outlined.List),
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

        AnimatedTab(Modifier.padding(paddingValues), selectedIndex, navController,roomViewmodel)

    }

}

@Composable
private fun AnimatedTab(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    navController: NavController,
    roomViewmodel: RoomViewmodel
) {
    Box(modifier) {
        AnimatedContent(targetState = selectedIndex, transitionSpec = {
            // Customize the animation with slide and fade effects
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
            }
        }
    }
}


//*===============================================================


@Composable
fun HomeScreen(roomViewmodel: RoomViewmodel) {

    val context = LocalContext.current
    var birthDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var calculateToDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

    val startDialog = rememberSaveable { mutableStateOf(false) }
    val endDialog = rememberSaveable { mutableStateOf(false) }

    // DatePickerDialog for selecting the birth date
    val birthDatePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            birthDate = LocalDate.of(year, month + 1, dayOfMonth)
            startDialog.value = false
        }, birthDate.year, birthDate.monthValue - 1, // Month is 0-based
        birthDate.dayOfMonth
    ).apply {
        setOnDismissListener {
            startDialog.value = false
        }

    }


    // DatePickerDialog for selecting the calculate-to date
    val calculateToDatePickerDialog = DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            calculateToDate = LocalDate.of(year, month + 1, dayOfMonth) // Month is 0-based
            endDialog.value = false
        }, calculateToDate.year, calculateToDate.monthValue - 1, // Month is 0-based
        calculateToDate.dayOfMonth
    ).apply {
        setOnDismissListener {
            endDialog.value = false
        }
    }

    if (startDialog.value) {
        birthDatePickerDialog.show()
    }
    if (endDialog.value) {
        calculateToDatePickerDialog.show()
    }

    // Calculate the age details when clicking the button
    val ageDetails = calculateAgeDetails(birthDate, calculateToDate)



    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {

            Spacer(
                Modifier.height(
                    100.dp
                )
            )

            Text("Developer : Jihan", style = MaterialTheme.typography.titleSmall)



            EditableTextField(
                label = "Start",
                week = birthDate.dayOfWeek.name,
                value = birthDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            ) {
                startDialog.value = true
            }
            Spacer(Modifier.height(10.dp))
            EditableTextField(
                label = "End",
                week = calculateToDate.dayOfWeek.name,
                value = calculateToDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            ) {
                endDialog.value = true
            }

            Spacer(Modifier.height(10.dp))

            var showInputDialog by rememberSaveable { mutableStateOf(false) }

            if (showInputDialog) InputDialog(context, onDismissed = { showInputDialog = false }) {

                val imagePath = it.imageUri?.let { uri ->
                    saveImage(context, uri)
                }



                roomViewmodel.insertAge(
                    AgeEntity(
                        name = it.name,
                        description = it.description,
                        start = birthDate!!,
                        imagePath = imagePath
                    )
                )
                birthDate = LocalDate.now()
                calculateToDate = LocalDate.now()
            }



            AnimatedVisibility((ageDetails.days != 0) or (ageDetails.months != 0) or (ageDetails.years != 0)) {
                Button(onClick = {
                    showInputDialog = true
                }) {
                    Text("Save To Database")
                }
            }

            Spacer(Modifier.height(20.dp))

            AnimatedVisibility(
                (ageDetails.days != 0) or (ageDetails.months != 0) or (ageDetails.years != 0),
                enter = slideInVertically(
                    spring(
                        Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessVeryLow,
                    )
                )
            ) {
                Column {

                    AgeDetails(ageDetails)
                }
            }


        }
    }
}


@Composable
fun MyText(text1: String, text2: String) {
    Row(Modifier.fillMaxWidth(.9f), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text1, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
        Text(text2, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
    }
    Spacer(Modifier.height(10.dp))
    Spacer(
        Modifier
            .height(1.dp)
            .fillMaxWidth(.9f)
            .background(
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
    )
}

// Editable TextField Composable
@Composable
private fun EditableTextField(
    label: String,
    value: String,
    week: String,
    onCLick: () -> Unit = {},
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(.9f), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                label, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                week, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface
            )
        }
        Card(
            onClick = onCLick, modifier = Modifier.fillMaxWidth(.9f), shape = RoundedCornerShape(10)
        ) {
            Text(
                value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }
    }
}


