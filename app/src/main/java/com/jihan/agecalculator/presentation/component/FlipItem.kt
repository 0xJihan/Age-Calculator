package com.jihan.agecalculator.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.jihan.agecalculator.R
import com.jihan.agecalculator.domain.room.AgeEntity
import com.jihan.agecalculator.domain.utils.Constants.TAG
import com.jihan.agecalculator.domain.utils.calculateAgeDetails
import com.jihan.agecalculator.domain.utils.toFormattedDate
import com.jihan.agecalculator.presentation.flipper.composable.FlippingView
import com.jihan.agecalculator.presentation.flipper.enum.CardFace
import com.jihan.agecalculator.presentation.screens.MyText
import java.time.LocalDate

@Composable
fun FlipItem(
    ageEntity: AgeEntity,
    detailButtonClicked :(Int)->Unit,
    onDelete: (AgeEntity) -> Unit,
) {


    var cardFace by remember { mutableStateOf(CardFace.Front) }



    FlippingView(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
        cardFace = cardFace,
        onViewClick = {
            cardFace = it.whatNext
        },
        frontView = {
            FrontPage(ageEntity) { onDelete(it) }
        },
        backView = {
            BackPage(ageEntity) {
                detailButtonClicked(ageEntity.id!!)
            }
        },
        duration = 1000,
        cornerSize = 10.dp,
        cardElevation = 8,
        cardColors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )

    )


}


@Composable
fun FrontPage(ageEntity: AgeEntity, onDelete: (AgeEntity) -> Unit = {}) {

    val result = calculateAgeDetails(ageEntity.start, LocalDate.now())

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {

        ListItem(headlineContent = {
            Text(
                ageEntity.name, fontSize = 25.sp, fontWeight = FontWeight.SemiBold
            )
        }, supportingContent = {
            Column(Modifier.fillMaxWidth()) {
                Text("${ageEntity.description}", fontSize = 21.sp)
                Text(
                    "Age: ${result.years} years ${result.months} months ${result.days} days",
                    fontSize = 18.sp
                )

            }
        }, trailingContent = {
            IconButton(onClick = {
                onDelete(ageEntity)
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        }, leadingContent = {
            Log.i(TAG, ageEntity.imagePath.toString())

            if (ageEntity.imagePath.toString() == "null") {
                val painter = rememberAsyncImagePainter(R.drawable.profile)
                CircularImage(
                    modifier = Modifier.size(80.dp), clickable = false, painter = painter
                )
            } else {
                val painter = rememberAsyncImagePainter(model = ageEntity.imagePath)
                CircularImage(
                    painter = painter, modifier = Modifier.size(80.dp), clickable = false
                )
            }
        }

        )

    }
}

@Composable
fun BackPage(ageEntity: AgeEntity, onDetailViewCLick: () -> Unit) {

    val ageDetails = calculateAgeDetails(ageEntity.start, LocalDate.now())

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {



            Text(
                "Name: ${ageEntity.name}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold
            )
            Text(
                ageEntity.start.toFormattedDate(), fontSize = 20.sp
            )

        Spacer(Modifier.height(10.dp))
        MyText(
            "Age:", "${ageDetails.years} years ${ageDetails.months} months ${ageDetails.days} days"
        )
        MyText("Left", ageDetails.nextBirthdays[0].totalMonthsAndDaysLeft)
        MyText("Total Months", "${ageDetails.totalMonths} months")
        MyText("Total Weeks", "${ageDetails.totalWeeks} weeks")
        MyText("Total Days", "${ageDetails.totalDays} days")

        ElevatedButton(
            onClick = onDetailViewCLick,
            elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(top = 8.dp)
        ) {
            Text("Show Details")
        }


    }
}

