package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview
@Composable
fun GettingStarted(
    onNavigateToOnboarding: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSignUpOne: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 24.sp,
            color = colorResource(id = R.color.accent_color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 112.dp)
        )

        Text(
            text = stringResource(R.string.find_next_ride),
            fontSize = 16.sp,
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

        Button(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(56.dp),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.accent_color),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.login_button), fontSize = 16.sp
            )
        }
        Button(
            onClick = onNavigateToSignUpOne,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.color_border),
                    shape = RoundedCornerShape(12.dp)
                ),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.button_white),
                contentColor = colorResource(id = R.color.color_btn)
            )
        ) {
            Text(
                text = stringResource(R.string.getting_started_register_button), fontSize = 16.sp
            )
        }
    }
}