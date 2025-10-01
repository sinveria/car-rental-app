package ru.sinveria.rentcar.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sinveria.rentcar.R

@Preview
@Composable
fun GettingStarted() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.getting_started_app_name),
            fontSize = 24.sp,
            color = Color(android.graphics.Color.parseColor("#2A1246")),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 112.dp)
        )

        Text(
            text = stringResource(R.string.getting_started_subtitle),
            fontSize = 16.sp,
            color = Color(android.graphics.Color.parseColor("#B2000000")),
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
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(56.dp),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor("#2A1246")),
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.getting_started_login_button),
                fontSize = 16.sp
            )
        }
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = Color(android.graphics.Color.parseColor("#D0D5DD")),
                    shape = RoundedCornerShape(12.dp)
                ),

            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor("#FFFFFFFF")),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = stringResource(R.string.getting_started_register_button),
                fontSize = 16.sp
            )
        }
    }
}