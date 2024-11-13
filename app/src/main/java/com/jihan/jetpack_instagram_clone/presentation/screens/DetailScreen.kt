package com.jihan.jetpack_instagram_clone.presentation.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.rememberAsyncImagePainter
import com.jihan.jetpack_instagram_clone.R
import com.jihan.jetpack_instagram_clone.domain.room.AgeEntity
import com.jihan.jetpack_instagram_clone.domain.utils.Constants.TAG
import com.jihan.jetpack_instagram_clone.domain.utils.calculateAgeDetails
import com.jihan.jetpack_instagram_clone.domain.viewmodel.DetailViewmodel
import com.jihan.jetpack_instagram_clone.presentation.component.CircularImage
import java.time.LocalDate


class DetailScreen(
    private val detailViewmodel: DetailViewmodel,
    private val onImageUpdate: (AgeEntity) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val ageEntity = detailViewmodel.ageEntity.value ?: return

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // State to manage the picked image
            var imageUriState by remember { mutableStateOf<Uri?>(null) }
            var imageSaveAble by remember { mutableStateOf(false) }

            // Image picker launcher
            val imageLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                    imageUriState = uri
                    imageUriState?.let { imageSaveAble = true } ?: run { imageSaveAble = false }
                }

            //? Determine which image to show: picked image, existing path from ageEntity, or default drawable
            val painter = rememberAsyncImagePainter(
                model = when {
                    imageUriState != null -> imageUriState
                    ageEntity.imagePath != null && ageEntity.imagePath != "null" -> Uri.parse(
                        ageEntity.imagePath
                    )

                    else -> R.drawable.person
                }
            )


            // Display the circular image with click functionality
            CircularImage(
                modifier = Modifier.size(250.dp),
                painter = painter,
                clickable = true,
                onClick = { imageLauncher.launch("image/*") }
            )

            // Show save button if an image has been picked
            AnimatedVisibility(visible = imageSaveAble) {
                OutlinedButton(onClick = {
                    Log.e(TAG, "Content: $imageUriState")
                    imageSaveAble = false
                }) {
                    Text("Save Image")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Show age details
            val ageDetails = remember { calculateAgeDetails(ageEntity.start, LocalDate.now()) }
            AgeDetails(ageDetails)
        }
    }
}

