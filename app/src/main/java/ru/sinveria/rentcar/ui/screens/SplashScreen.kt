package ru.sinveria.rentcar.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.sinveria.rentcar.R

@Preview
@Composable
fun SplashScreen(onSplashComplete: () -> Unit = {}) {

    LaunchedEffect(Unit) {
        delay(2500)
        onSplashComplete()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 24.sp,
            color = colorResource(id = R.color.accent_color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 112.dp)
        )

        Text(
            text = stringResource(id = R.string.find_next_ride),
            fontSize = 16.sp,
            color = colorResource(id = R.color.color_black),
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

