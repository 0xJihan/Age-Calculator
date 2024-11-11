package com.jihan.jetpack_instagram_clone.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jihan.jetpack_instagram_clone.R
import com.jihan.jetpack_instagram_clone.component.FlipItem
import com.jihan.jetpack_instagram_clone.component.SearchView
import com.jihan.jetpack_instagram_clone.room.RoomViewmodel

class ListScreen : Tab {
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                0u, "List", null
            )
        }

    @Composable
    override fun Content() {

        val roomViewModel = hiltViewModel<RoomViewmodel>()
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
                    .padding(10.dp), placeholder = "Search here..."
            ) {
                roomViewModel.searchAges("%$it%")
            }

            if (list.isEmpty()){
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center){
                    Image(
                        modifier = Modifier.size(300.dp),
                        painter = painterResource(R.drawable.no_data),
                        contentDescription = "No Data",
                    )
                }
            }


            LazyColumn {


                items(list) {

                    Box(
                        Modifier.animateItem(
                        )
                    ) {

                        FlipItem(it) { entitiy ->
                            roomViewModel.deleteAge(entitiy)
                        }

                    }
                }

            }


        }
    }
}