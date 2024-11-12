package com.jihan.jetpack_instagram_clone.presentation.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import com.jihan.jetpack_instagram_clone.R
import com.jihan.jetpack_instagram_clone.domain.room.AgeEntity
import com.jihan.jetpack_instagram_clone.domain.utils.*
import com.jihan.jetpack_instagram_clone.domain.utils.Constants.TAG
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

        // State variables
        val ageEntity = detailViewmodel.ageEntity.value ?: return
        var imageUri by remember { mutableStateOf<Uri?>(ageEntity.imagePath?.toUri()) }
        val ageDetails = remember { calculateAgeDetails(ageEntity.start, LocalDate.now()) }

        val imageModel by remember { mutableStateOf<Any?>(ageEntity.imagePath ?: R.drawable.person) }
        var isImageUpdateable by remember { mutableStateOf(false) }

        // Image picker launcher
//        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                imageModel = it
//                isImageUpdateable = true
//            }
//        }

        // Save the selected image if it changes
//        LaunchedEffect(imageUri) {
//            imageUri?.let {
//                imageModel = it
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularImage(
                modifier = Modifier.size(250.dp),
                model = imageModel,
                clickable = false
            )

            AnimatedVisibility(visible = isImageUpdateable) {
                ElevatedButton(onClick = {
                    val imagePath = saveImage(context, (imageModel as? Uri)?.toString()?.toUri(), uniqueImageName())
                    Log.d(TAG, "Selected URI: ${imageModel?.toString()?.toUri()}")
                    Log.d(TAG, "Saved file path: $imagePath")

                    onImageUpdate(ageEntity.copy(imagePath = imagePath))
                    isImageUpdateable = false
                }) {
                    Text("Update Image")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AgeDetails(ageDetails)
        }
    }
}
