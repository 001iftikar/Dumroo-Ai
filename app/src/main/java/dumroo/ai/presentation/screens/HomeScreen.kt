package dumroo.ai.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dumroo.ai.R
import dumroo.ai.domain.model.Tool
import dumroo.ai.domain.model.ToolInputField
import dumroo.ai.presentation.components.ThemeBackground
import dumroo.ai.presentation.components.ToolItem
import dumroo.ai.presentation.components.TopBarIcon

@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit = {}
) {
    ThemeBackground(isDarkTheme = isDarkTheme) {

        Scaffold(
            containerColor = Color.Transparent, // Important!
            topBar = {
                TopBar(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp), // Screen side padding
                    verticalArrangement = Arrangement.spacedBy(12.dp), // Space between cards
                    contentPadding = PaddingValues(bottom = 20.dp) // Bottom scroll padding
                ) {
                    // "Popular for You" Header
                    item {
                        Text(
                            text = "Popular for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) Color.White else Color.Black,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    // The List of Tools
                    items(
                        items = dummyTools,
                        key = { it.title }
                    ) { tool ->
                        ToolItem(
                            tool = tool,
                            isDarkTheme = isDarkTheme, // Pass the theme boolean directly
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit = {}
) {
    // 1. Determine Text/Icon Color
    val contentColor = if (isDarkTheme) Color.White else Color.Black
    val subTextColor = Color.Gray

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = contentColor,
            actionIconContentColor = subTextColor
        ),
        title = {
            Greeting(
                name = "Sarah",
                titleColor = contentColor,
                subtitleColor = subTextColor
            )
        },
        actions = {
            TopBarIcon(
                icon = R.drawable.baseline_menu_24,
                onClick = {}
            )
            TopBarIcon(
                icon = if (isDarkTheme) R.drawable.baseline_light_mode_24 else R.drawable.baseline_dark_mode_24,
                onClick = onThemeToggle
            )
            TopBarIcon(
                icon = R.drawable.outline_notifications_24,
                onClick = {}
            )
            TopBarIcon(
                icon = R.drawable.outline_person_24,
                onClick = {}
            )

        }
    )
}

@Composable
private fun Greeting(
    name: String,
    titleColor: Color,
    subtitleColor: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = "Hi $name!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = titleColor
        )
        Text(
            text = "What are you looking for today?",
            style = MaterialTheme.typography.bodyLarge,
            color = subtitleColor
        )
    }
}


val dummyTools = listOf(
    Tool(
        id = "5e_plan",
        title = "5E Model Lesson Plan",
        icon = R.drawable.baseline_calendar_today_24,
        tint = Color(0xFF3B82F6), // Blue
        description = "Create a 5E model lesson plan.",
        uniqueInputs = listOf(
            ToolInputField("subject", "Subject", "e.g., Science", InputType.Text),
            ToolInputField("grade", "Grade level", "e.g., 5th grade", InputType.Text),
            ToolInputField("topic", "Specific topic or concept", "e.g., The Water Cycle", InputType.Text),
            ToolInputField("duration", "Duration of the lesson", "e.g., 45 minutes", InputType.Text)
        )
    ),
    Tool(
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
    Tool(
        id = "class_newsletter",
        title = "Class Newsletter",
        icon = R.drawable.outline_chat_bubble_24,
        tint = Color(0xFF10B981), // Green
        description = "Generate a newsletter for your class.",
        uniqueInputs = listOf(
            ToolInputField("classroom_activities", "Classroom activities", "Enter classroom activities", InputType.Text),
            ToolInputField("upcoming_events", "Upcoming events", "Enter upcoming events", InputType.Text),
            ToolInputField("announcements", "Important announcements", "Enter important announcements", InputType.Text)
        )
    ),
    Tool(
        id = "diff_planner",
        title = "Differentiation Planner",
        icon = R.drawable.baseline_calendar_today_24,
        tint = Color(0xFF3B82F6), // Blue
        description = "Plan differentiated instruction.",
        uniqueInputs = listOf(
            ToolInputField("subject", "Subject", "e.g., Science", InputType.Text),
            ToolInputField("grade", "Grade level", "e.g., 5th grade", InputType.Text),
            ToolInputField("topic", "Specific topic or concept", "e.g., The Water Cycle", InputType.Text),
            ToolInputField("duration", "Duration of the lesson", "e.g., 45 minutes", InputType.Text)
        )
    ),
    Tool(
        id = "email_responder",
        title = "Email Responder",
        icon = R.drawable.outline_chat_bubble_24,
        tint = Color(0xFF10B981), // Green
        description = "Draft professional email responses.",
        uniqueInputs = listOf(
            ToolInputField("received_content", "Received email content", "Enter received email content", InputType.Text),
            ToolInputField("response_requirements", "Response requirements", "Enter response requirements", InputType.Text)
        )
    ),
    Tool(
        id = "game_designer",
        title = "Game Designer",
        icon = R.drawable.outline_gamepad_circle_down_24,
        tint = Color.Gray, // Default gray
        description = "Design educational games.",
        uniqueInputs = listOf(
            ToolInputField("subject", "Subject", "Enter subject", InputType.Text),
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("learning_objective", "Learning objective", "Enter learning objective", InputType.Text),
            ToolInputField("game_type", "Game type", "Enter game type", InputType.Text)
        )
    ),
    Tool(
        id = "iep_gen",
        title = "IEP Generator",
        icon = R.drawable.baseline_group_24,
        tint = Color(0xFF8B5CF6), // Magenta
        description = "Generate Individualized Education Programs.",
        uniqueInputs = listOf(
            ToolInputField("student_needs", "Student's needs", "Enter student's needs", InputType.Text),
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("goals", "Specific goals and accommodations", "Enter specific goals and accommodations", InputType.Text)
        )
    ),
    Tool(
        id = "lesson_plan_gen",
        title = "Lesson Plan Generator",
        icon = R.drawable.baseline_calendar_today_24,
        tint = Color(0xFF3B82F6), // Blue
        description = "Generate standard lesson plans.",
        uniqueInputs = listOf(
            ToolInputField("subject", "Subject", "Enter subject", InputType.Text),
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("topic", "Topic", "Enter topic", InputType.Text),
            ToolInputField("duration", "Duration", "Enter duration", InputType.Text),
            ToolInputField("objectives", "Learning objectives", "Enter learning objectives", InputType.Text)
        )
    ),
    Tool(
        id = "leveler",
        title = "Leveler",
        icon = R.drawable.outline_gamepad_circle_down_24, // Using gamepad icon as placeholder or specific level icon
        tint = Color.Black,
        description = "Adjust content reading levels.",
        uniqueInputs = listOf(
            ToolInputField("content", "Content/activity", "Enter content/activity", InputType.Text),
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("subject", "Subject area", "Enter subject area", InputType.Text),
            ToolInputField("num_levels", "Number of levels", "Enter number of levels", InputType.Text),
            ToolInputField("specific_objectives", "Specific learning objectives", "Enter specific learning objectives", InputType.Text)
        )
    ),
    Tool(
        id = "ptc_planner",
        title = "Parent-Teacher Conference Planner",
        icon = R.drawable.outline_chat_bubble_24,
        tint = Color(0xFF10B981), // Green
        description = "Plan for parent-teacher conferences.",
        uniqueInputs = listOf(
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("student_progress", "Student progress", "Enter student progress", InputType.Text),
            ToolInputField("areas_concern", "Areas of concern", "Enter areas of concern", InputType.Text),
            ToolInputField("duration", "Meeting duration", "Enter meeting duration", InputType.Text)
        )
    )
)
























