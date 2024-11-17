package com.jihan.agecalculator.presentation.component

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.jihan.agecalculator.R
import com.jihan.agecalculator.domain.model.InputResponse

@Composable
fun InputDialog(
    context: Context,
    onDismissed: (Boolean) -> Unit,
    onSaveClick: (InputResponse) -> Unit,
) {


    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }




    AlertDialog(onDismissRequest = { onDismissed(false) }, confirmButton = {
        TextButton(onClick = {
            if (name.isNotEmpty()) {
                onSaveClick(InputResponse(name, description, imageUri))
                Toast.makeText(
                    context, "Information added to database successfully", Toast.LENGTH_SHORT
                ).show()
                onDismissed(false)
            } else {
                Toast.makeText(context, "Please enter a name first", Toast.LENGTH_SHORT).show()
            }
        }) { Text("Save") }
    }, text = {
        Content(name, description, imageUri, onDescriptionChanged = { description = it },
            onIconSelected = {
                imageUri = it
            }) { name = it }
    }, dismissButton = {
        TextButton(onClick = { onDismissed(false) }) { Text("Cancel") }
    })

}

@Composable
private fun Content(
    name: String,
    desc: String,
    imageUri: Uri?,
    onIconSelected: (Uri?) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
) {

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            onIconSelected(it)
        }

        val painter = rememberAsyncImagePainter(
            model = imageUri ?: R.drawable.person
        )
        CircularImage(Modifier.size(100.dp), painter ) {
            launcher.launch("image/*")
        }


        Text("Save Data", fontSize = 25.sp, color = MaterialTheme.colorScheme.secondary)
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(.9f),
            singleLine = true,
            value = name,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = {
                if (it.length <= 50)
                    onNameChanged(it)
                 },
            label = { Text("Enter Name (required)") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(modifier = Modifier.fillMaxWidth(.9f),
            maxLines = 3,
            value = desc,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onValueChange = {
                if (it.length <= 150)
                    onDescriptionChanged(it)
            },
            label = { Text("Enter Description (optional)") })


    }

}