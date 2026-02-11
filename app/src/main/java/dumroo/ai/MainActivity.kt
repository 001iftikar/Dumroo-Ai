package dumroo.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dumroo.ai.presentation.navigation.NavGraph
import dumroo.ai.ui.theme.DumrooAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isOnDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(isOnDark) }
            DumrooAiTheme(
                darkTheme = isDarkTheme
            ) {
                NavGraph(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = {isDarkTheme = !isDarkTheme}
                )
            }
        }
    }
}