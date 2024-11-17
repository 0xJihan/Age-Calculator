package com.jihan.agecalculator.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jihan.agecalculator.R
import com.jihan.agecalculator.domain.utils.deleteImage
import com.jihan.agecalculator.domain.viewmodel.RoomViewmodel
import com.jihan.agecalculator.presentation.component.FlipItem
import com.jihan.agecalculator.presentation.component.SearchView


@Composable
fun ListScreen(
    roomViewModel: RoomViewmodel,
    buttonClick: (Int) -> Unit,
) {
    val list by roomViewModel.ageList.collectAsStateWithLifecycle()



    Column(
        Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface
            )
    ) {


        SearchView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 10.dp, end = 10.dp),
            placeholder = "Search here..."
        ) {
            roomViewModel.searchAges("%$it%")
        }

        if (list.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        modifier = Modifier.size(300.dp),
                        painter = painterResource(R.drawable.no_data),
                        contentDescription = "No Data",
                    )

                    Text(text = "No Data Found", style = MaterialTheme.typography.headlineSmall)
                }
            }
        }


        LazyColumn {


            items(list) {

                Box(
                    Modifier.animateItem(
                    )
                ) {

                    FlipItem(it, detailButtonClicked = {
                        buttonClick(it)
                    }) { entity ->
                        roomViewModel.deleteAge(entity)
                        entity.imagePath?.let {
                            deleteImage(it)
                        }
                    }

                }
            }

        }


    }
}