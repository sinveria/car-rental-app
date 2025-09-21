package ru.sinveria.rentcar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "DriveNext",
            fontSize = 24.sp,
            color = Color(0xFF2A1246),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 112.dp)
        )

        Text(
            text = "Поможем найти твою следующую поездку",
            fontSize = 16.sp,
            color = Color(0xB2000000),
            modifier = Modifier.padding(top = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.splash_image),
            contentDescription = "Splash image",
            modifier = Modifier
                .padding(top = 80.dp)
                .size(343.dp)
        )
    }
}