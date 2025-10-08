package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview(showBackground = true)
@Composable
fun Onboarding(
    onNavigateToGettingStarted: () -> Unit = {}
) {
    var currentScreen by rememberSaveable { mutableIntStateOf(0) }

    when (currentScreen) {
        0 -> OnboardingPage(
            currentScreen = currentScreen,
            onNext = { currentScreen++ },
            onSkip = { onNavigateToGettingStarted() },
            imageRes = R.drawable.onboadring_one,
            titleRes = R.string.onboarding_one_title,
            descriptionRes = R.string.onboarding_one_description,
            isLastScreen = false
        )
        1 -> OnboardingPage(
            currentScreen = currentScreen,
            onNext = { currentScreen++ },
            onSkip = { onNavigateToGettingStarted() },
            imageRes = R.drawable.onboarding_two,
            titleRes = R.string.onboarding_two_title,
            descriptionRes = R.string.onboarding_two_description,
            isLastScreen = false
        )
        2 -> OnboardingPage(
            currentScreen = currentScreen,
            onNext = { onNavigateToGettingStarted() },
            onSkip = { onNavigateToGettingStarted() },
            imageRes = R.drawable.onboarding_three,
            titleRes = R.string.onboarding_three_title,
            descriptionRes = R.string.onboarding_three_description,
            isLastScreen = true
        )
    }
}

@Composable
fun OnboardingPage(
    currentScreen: Int,
    onNext: () -> Unit,
    onSkip: () -> Unit,
    imageRes: Int,
    titleRes: Int,
    descriptionRes: Int,
    isLastScreen: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Car image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
                .height(343.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onSkip) {
                    Text(
                        text = stringResource(R.string.onboarding_skip),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.accent_color),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(343.dp + 32.dp))

            Text(
                text = stringResource(titleRes),
                fontSize = 24.sp,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(descriptionRes),
                fontSize = 14.sp,
                color = colorResource(id = R.color.description),
                textAlign = TextAlign.Start,
                lineHeight = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 0..2) {
                        val dotColor = if (i == currentScreen) {
                            R.color.accent_color
                        } else {
                            R.color.dot_inactive
                        }

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .width(40.dp)
                                .height(8.dp),
                            shape = RoundedCornerShape(2.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = dotColor)
                            ),
                            content = {}
                        )
                    }
                }

                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .width(131.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.accent_color),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isLastScreen) {
                            stringResource(R.string.heregoes)
                        } else {
                            stringResource(R.string.next)
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}