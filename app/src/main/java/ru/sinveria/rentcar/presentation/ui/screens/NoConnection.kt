package ru.sinveria.rentcar.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.viewmodel.NoConnectionViewModel

@Composable
fun NoConnection(
    viewModel: NoConnectionViewModel = hiltViewModel(),
    onConnectionRestored: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.no_conn),
            contentDescription = "No connection image",
            modifier = Modifier
                .padding(top = 80.dp)
                .width(96.dp)
                .height(96.dp)
                .padding(bottom = 32.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.no_connection),
                style = MaterialTheme.typography.titleLarge,
                color = colorResource(id = R.color.accent_color),
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Text(
                text = stringResource(id = R.string.try_again_connection),
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.accent_color),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onRetryClick()
                viewModel.checkConnection()
            },
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
                text = stringResource(id = R.string.retry),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}