package dumroo.ai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dumroo.ai.presentation.screens.HomeScreen
import dumroo.ai.presentation.screens.ToolDetailScreen
import dumroo.ai.presentation.screens.dummyTools

@Composable
fun NavGraph(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = Route.HomeScreen
    ) {
        composable<Route.HomeScreen> {
            HomeScreen(
                navHostController = navHostController,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }

        composable<Route.ToolDetailsScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.ToolDetailsScreen>()
            val selectedTool = dummyTools.find { it.id == args.toolId }

            if (selectedTool != null) {
                ToolDetailScreen(
                    tool = selectedTool,
                    isDarkTheme = isDarkTheme,
                    onBackClick = { navHostController.popBackStack() }
                )
            }
        }
    }
}




















