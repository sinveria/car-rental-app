import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ru.sinveria.rentcar.ui.screens.NoConnection
import ru.sinveria.rentcar.ui.screens.SplashScreen
import ru.sinveria.rentcar.utils.NetworkUtils

@Preview
@Composable
fun App() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        NetworkUtils.initialize(context)
    }

    val isConnected by NetworkUtils.isConnected.collectAsState()
    // val isConnected = true переменная для теста

    if (isConnected) {
        SplashScreen(
            onSplashComplete = {
                // реализовать переход на главный экран после загрузки
                // что-то вроде navController.navigate("mainScreen")
            }
        )
    } else {
        NoConnection(
            onConnectionRestored = {
                // срабатывает при автоматическом восстановлении подключения
            },
            onRetryClick = {
                val isAvailable = NetworkUtils.isInternetAvailable(context)
                if (isAvailable) {
                    // принудительно обновить состояние, если подключение восстановилось
                }
            }
        )
    }
}