package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.sinveria.rentcar.R

@Composable
fun AddCarPhotos(
    onNavigateBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.go_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNavigateBack() }
            )
            Text(
                text = stringResource(id = R.string.add_car_photos_title),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(elevation = 4.dp)
                .background(Color.LightGray)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {

            }

            Spacer(modifier = Modifier.width(16.dp))


            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {

            }

            Spacer(modifier = Modifier.width(16.dp))


            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp)
                    .shadow(elevation = 4.dp)
                    .background(Color.LightGray)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {

            }
        }

        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent_color),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.next),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}