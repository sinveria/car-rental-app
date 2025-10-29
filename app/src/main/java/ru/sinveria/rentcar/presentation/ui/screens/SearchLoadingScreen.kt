package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.sinveria.rentcar.R

@Composable
fun SearchLoadingScreen() {
    val dot1Alpha = remember { Animatable(0.3f) }
    val dot2Alpha = remember { Animatable(0.3f) }
    val dot3Alpha = remember { Animatable(0.3f) }

    LaunchedEffect(Unit) {
        dot1Alpha.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween<Float>(durationMillis = 600),
                repeatMode = RepeatMode.Reverse
            )
        )

        kotlinx.coroutines.delay(200)
        dot2Alpha.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween<Float>(durationMillis = 600),
                repeatMode = RepeatMode.Reverse
            )
        )

        kotlinx.coroutines.delay(400)
        dot3Alpha.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween<Float>(durationMillis = 600),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.machine_search),
                contentDescription = "Searching cars",
                modifier = Modifier
                    .size(200.dp)
            )

            Text(
                text = stringResource(id = R.string.find_suitable_cars),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .alpha(dot1Alpha.value)
                        .background(
                            color = Color.Black,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .alpha(dot2Alpha.value)
                        .background(
                            color = Color.Black,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                        .padding(horizontal = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .alpha(dot3Alpha.value)
                        .background(
                            color = Color.Black,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
            }
        }
    }
}