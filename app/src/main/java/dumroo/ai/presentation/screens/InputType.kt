package dumroo.ai.presentation.screens


sealed class InputType {
    object Text : InputType()
    object TextArea : InputType()
    data class Dropdown(val options: List<String>) : InputType()
}