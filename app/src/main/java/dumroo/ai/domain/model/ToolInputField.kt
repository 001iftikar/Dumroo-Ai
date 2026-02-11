package dumroo.ai.domain.model

import dumroo.ai.presentation.screens.InputType

data class ToolInputField(
    val id: String,
    val label: String,
    val placeholder: String,
    val type: InputType
)