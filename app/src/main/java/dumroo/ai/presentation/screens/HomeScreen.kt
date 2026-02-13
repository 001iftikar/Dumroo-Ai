package dumroo.ai.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dumroo.ai.R
import dumroo.ai.domain.model.Tool
import dumroo.ai.domain.model.ToolInputField
import dumroo.ai.presentation.components.ThemeBackground
import dumroo.ai.presentation.components.ToolItem
import dumroo.ai.presentation.components.TopBarIcon
import dumroo.ai.presentation.navigation.Route

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val name = "Sarah" // state, will come from api
            ThemeBackground(isDarkTheme = isDarkTheme) {
            Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopBar(
                    userName = name,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding())
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        Text(
                            text = "Popular for You",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) Color.White else Color.Black,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    items(dummyTools) { tool ->
                        ToolItem(
                            tool = tool,
                            isDarkTheme = isDarkTheme,
                            onClick = {
                                navHostController.navigate(Route.ToolDetailsScreen(tool.id))
                            }
                        )
                    }
                }

                HomeBottomPanel(isDarkTheme = isDarkTheme)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    userName: String,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit = {}
) {
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
                name = userName,
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
        tint = Color(0xFF3B82F6),
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
        tint = Color(0xFF8B5CF6),
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
        tint = Color(0xFF10B981),
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
        tint = Color(0xFF3B82F6),
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
        tint = Color(0xFF10B981),
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
        tint = Color.Gray,
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
        tint = Color(0xFF8B5CF6),
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
        tint = Color(0xFF3B82F6),
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
        icon = R.drawable.outline_gamepad_circle_down_24,
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
        tint = Color(0xFF10B981),
        description = "Plan for parent-teacher conferences.",
        uniqueInputs = listOf(
            ToolInputField("grade", "Grade level", "Enter grade level", InputType.Text),
            ToolInputField("student_progress", "Student progress", "Enter student progress", InputType.Text),
            ToolInputField("areas_concern", "Areas of concern", "Enter areas of concern", InputType.Text),
            ToolInputField("duration", "Meeting duration", "Enter meeting duration", InputType.Text)
        )
    )
)

@Composable
private fun HomeBottomPanel(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val panelColor = if (isDarkTheme) Color.Black.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.9f)
    val searchBarColor = if (isDarkTheme) Color(0xFF1F2937) else Color(0xFFE5E7EB)
    val subTextColor = if (isDarkTheme) Color.Gray else Color.DarkGray
    val contentColor = if (isDarkTheme) Color.White else Color.Black

    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("ContentAI") }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = panelColor,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryDropdown(isDarkTheme = isDarkTheme)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Outlined.Star, null, tint = subTextColor, modifier = Modifier.size(16.dp))
                    Text("3", style = MaterialTheme.typography.labelMedium, color = contentColor)
                }
                Text("Browse all 10 tools", style = MaterialTheme.typography.labelSmall, color = subTextColor)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(searchBarColor)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, "Search", tint = subTextColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {searchQuery = it},
                        placeholder = {
                            Text(
                                "Search AI Tools...",
                                color = subTextColor.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isDarkTheme) Color.Black.copy(alpha = 0.5f) else Color(
                            0xFFD1D5DB
                        )
                    )
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { selectedTab = "Library" },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painterResource(id = R.drawable.outline_menu_book_24), "Library", tint = subTextColor)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E3A8A))
                        .clickable { selectedTab = "ContentAI" },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.baseline_auto_awesome_24), null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ContentAI", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryDropdown(isDarkTheme: Boolean) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All Tools") }

    val categories = listOf(
        "All Tools", "Lesson Planning", "Assessment", "Communication",
        "Engagement", "Content Creation", "Questions", "Student Support",
        "Differentiation", "Intellectual Prep", "Community Tools",
        "Literacy", "Social-Emotional"
    )

    val contentColor = if (isDarkTheme) Color.White else Color.Black

    val dropdownBg = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.8f)
    } else {
        Color.White.copy(alpha = 0.95f)
    }

    Box {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = selectedCategory,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = if (isDarkTheme) Color.Gray else Color.DarkGray,
                modifier = Modifier.size(16.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 300.dp)
                .background(Color.Transparent),
            containerColor = dropdownBg,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, if (isDarkTheme) Color.White.copy(0.1f) else Color.Black.copy(0.1f))
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = category,
                            color = contentColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        selectedCategory = category
                        expanded = false
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}





















