package com.jihan.jetpack_instagram_clone.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputDialog(context: Context, onDismissed: (Boolean) -> Unit, onSaveClick: (Pair<String,String>) -> Unit) {


    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }




    AlertDialog(onDismissRequest = { onDismissed(false) }, confirmButton = {
        TextButton(onClick = {
            if (name.isNotEmpty()) {
                onSaveClick(Pair(name, description))
                Toast.makeText(
                    context, "Information added to database successfully", Toast.LENGTH_SHORT
                ).show()
                onDismissed(false)
            } else {
                Toast.makeText(context, "Please enter a name first", Toast.LENGTH_SHORT).show()
            }
        }) { Text("Save") }
    }, text = {
        Content(name, description, onDescriptionChanged = { description = it }) { name = it }
    }, dismissButton = {
        TextButton(onClick = { onDismissed(false) }) { Text("Cancel") }
    })

}

@Composable
private fun Content(
    name: String,
    desc: String,
    onDescriptionChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
) {

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Save Data", fontSize = 25.sp, color = MaterialTheme.colorScheme.errorContainer)
        Spacer(Modifier.height(5.dp))
        OutlinedTextField(modifier = Modifier.fillMaxWidth(.9f),
            value = name,
            onValueChange = { onNameChanged(it) },
            label = { Text("Enter Name") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(modifier = Modifier.fillMaxWidth(.9f),
            value = desc,
            onValueChange = { onDescriptionChanged(it) },
            label = { Text("Enter Description") })


    }

}