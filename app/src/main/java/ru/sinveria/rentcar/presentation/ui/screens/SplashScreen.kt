package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.viewmodel.SplashViewModel
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit = {}, onNoConnection: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val isConnected by viewModel.isConnected.collectAsState()

    LaunchedEffect(isConnected) {
        if (isConnected) {
            kotlinx.coroutines.delay(2000)
            onSplashComplete()
        } else {
            onNoConnection()
        }
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
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.accent_color),
            modifier = Modifier.padding(top = 112.dp)
        )

        Text(
            text = stringResource(id = R.string.find_next_ride),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.accent_color),
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

