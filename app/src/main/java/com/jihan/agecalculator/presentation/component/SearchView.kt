package com.jihan.agecalculator.presentation.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    corner: Int = 0,
    elevation: Int = 4,
    searchIcon: ImageVector = Icons.Default.Search,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        focusedLabelColor = Color.DarkGray
    ),
    placeholder: String? = null,
    onQueryChange: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }





    Card(
        modifier = modifier.background(Color.Transparent),
        shape = RoundedCornerShape(corner),
        elevation = CardDefaults.cardElevation(elevation.dp)
    ) {


        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onQueryChange(it)
            },
            placeholder = {
                placeholder?.let {
                    Text(it)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = searchIcon, contentDescription = null
                )
            },
            colors = colors,
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear text")
                    }
                }
            },

            )
    }

}




