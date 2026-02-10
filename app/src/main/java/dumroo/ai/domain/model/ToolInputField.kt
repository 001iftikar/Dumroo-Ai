package dumroo.ai.domain.model

import dumroo.ai.presentation.screens.InputType

data class ToolInputField(
    val id: String,          // Key for the API (e.g., "grade_level")
    val label: String,       // UI Label (e.g., "Grade Level")
    val placeholder: String, // UI Hint
    val type: InputType
)