package dumroo.ai.domain.model

import androidx.compose.ui.graphics.Color

data class Tool(
    val id: String,
    val title: String,
    val icon: Int,
    val tint: Color = Color.Unspecified,
    val description: String,
    val uniqueInputs: List<ToolInputField>
)
