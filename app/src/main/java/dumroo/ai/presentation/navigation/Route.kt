package dumroo.ai.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeScreen : Route
    @Serializable
    data class ToolDetailsScreen(val toolId: String) : Route
}