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
import androidx.compose.ui.graphics.Color
import dumroo.ai.domain.model.Tool
import dumroo.ai.domain.model.ToolInputField
import dumroo.ai.presentation.screens.HomeScreen
import dumroo.ai.presentation.screens.InputType
import dumroo.ai.presentation.screens.ToolDetailScreen
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
//                HomeScreen(
//                    isDarkTheme = isDarkTheme,
//                    onThemeToggle = { isDarkTheme = !isDarkTheme }
//                )
                ToolDetailScreen(
                    tool = Tool(
                        id = "behavior_plan",
                        title = "Behavior Intervention Plan",
                        icon = R.drawable.baseline_group_24,
                        tint = Color(0xFF8B5CF6), // Violet/Magenta
                        description = "Create a behavior intervention plan.",
                        uniqueInputs = listOf(
                            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
                            ToolInputField("target_behavior", "Target behavior", "Enter target behavior", InputType.Text),
                            ToolInputField("behavior_function", "Behavior function", "Enter behavior function", InputType.Text),
                            ToolInputField("strategies", "Current strategies", "Enter current strategies", InputType.Text)
                        )
                    ),
                    isDarkTheme = isDarkTheme
                ) { }
            }
        }
    }
}