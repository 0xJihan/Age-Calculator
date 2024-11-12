package com.jihan.jetpack_instagram_clone.presentation.screens

import android.util.Log
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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.jihan.jetpack_instagram_clone.R
import com.jihan.jetpack_instagram_clone.presentation.component.FlipItem
import com.jihan.jetpack_instagram_clone.presentation.component.SearchView
import com.jihan.jetpack_instagram_clone.domain.viewmodel.RoomViewmodel
import com.jihan.jetpack_instagram_clone.domain.utils.Constants.TAG
import com.jihan.jetpack_instagram_clone.domain.utils.deleteImage

class ListScreen : Tab {
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                0u, "List", null
            )
        }

    @Composable
    override fun Content() {


        ItemList(roomViewModel = hiltViewModel<RoomViewmodel>())
    }

    @Composable
    private fun ItemList(
        roomViewModel: RoomViewmodel,
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
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
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

                        FlipItem(it, onImageUpdate = {
                            roomViewModel.updateAge(it)
                            deleteImage(it.imagePath)
                        }) { entitiy ->
                            roomViewModel.deleteAge(entitiy)
                            entitiy.imagePath?.let {
                                val isDeleted = deleteImage(it)
                                Log.i(TAG, "ItemList: $isDeleted")
                            }
                        }

                    }
                }

            }


        }
    }
}