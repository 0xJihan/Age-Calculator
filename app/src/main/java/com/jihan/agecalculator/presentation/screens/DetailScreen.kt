package com.jihan.agecalculator.presentation.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.rememberAsyncImagePainter
import com.jihan.agecalculator.R
import com.jihan.agecalculator.domain.room.AgeEntity
import com.jihan.agecalculator.domain.utils.calculateAgeDetails
import com.jihan.agecalculator.domain.utils.deleteImage
import com.jihan.agecalculator.domain.utils.saveImage
import com.jihan.agecalculator.domain.viewmodel.DetailViewmodel
import com.jihan.agecalculator.presentation.component.CircularImage
import java.time.LocalDate


class DetailScreen(
    private val detailViewmodel: DetailViewmodel,
    private val onImageUpdate: (AgeEntity) -> Unit,
) : Screen {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val ageEntity = detailViewmodel.ageEntity.value ?: return

        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.surface,
                            )
                        ))
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
                    AnimatedButton(imageSaveAble, context, imageUriState, ageEntity) { imageSaveAble = it }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Show age details
                    val ageDetails = remember { calculateAgeDetails(ageEntity.start, LocalDate.now()) }
                    AgeDetails(ageDetails)
                }
            }
        }


    }

    @Composable
    private fun ColumnScope.AnimatedButton(
        imageSaveAble: Boolean,
        context: Context,
        imageUriState: Uri?,
        ageEntity: AgeEntity,
        onImageSaved: (Boolean) -> Unit = {},
    ) {
        var isLoading by remember { mutableStateOf(false) }
        AnimatedVisibility(visible = imageSaveAble) {
            OutlinedButton(onClick = {
                isLoading = true

                val newImagePath = saveImage(context, imageUriState!!)
                onImageUpdate(ageEntity.copy(imagePath = newImagePath))

                deleteImage(ageEntity.imagePath) //? Delete the old image

                // Reset states after saving
                isLoading = false
                onImageSaved(false)
                Toast.makeText(context, "Image Saved", Toast.LENGTH_SHORT).show()

            }, enabled = isLoading.not()) {
                if (isLoading) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text("Saving...")
                        Spacer(modifier = Modifier.width(8.dp))
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    }
                } else {
                    Text("Save Image")
                }
            }
        }
    }

}

