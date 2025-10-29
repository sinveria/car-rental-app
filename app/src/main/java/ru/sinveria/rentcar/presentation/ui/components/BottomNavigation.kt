package ru.sinveria.rentcar.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.sinveria.rentcar.R
import ru.sinveria.rentcar.presentation.navigation.Screen

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    currentScreen: String = Screen.Home.route,
    onHomeClick: () -> Unit = {},
    onBookmarksClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .shadow(
                elevation = 16.dp
            )
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 68.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = if (currentScreen == Screen.Home.route) {
                        R.drawable.home_active
                    } else {
                        R.drawable.home
                    }
                ),
                contentDescription = "Home",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onHomeClick() }
            )

            Image(
                painter = painterResource(id = R.drawable.bookmark),
                contentDescription = "Bookmarks",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBookmarksClick() }
            )

            Image(
                painter = painterResource(
                    id = if (currentScreen == Screen.Settings.route || currentScreen == Screen.UserProfile.route) {
                        R.drawable.settings_active
                    } else {
                        R.drawable.settings
                    }
                ),
                contentDescription = "Settings",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick() }
            )
        }
    }
}