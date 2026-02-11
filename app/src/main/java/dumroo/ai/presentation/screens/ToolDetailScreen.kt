package dumroo.ai.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dumroo.ai.R
import dumroo.ai.domain.model.Tool
import dumroo.ai.domain.model.ToolInputField
import dumroo.ai.presentation.components.ThemeBackground

@Composable
fun ToolDetailScreen(
    tool: Tool,
    isDarkTheme: Boolean,
    onBackClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Chat") }
    var isInputPanelVisible by remember { mutableStateOf(true) }

    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedCurriculum by remember { mutableStateOf("") }
    val toolFormState = remember(tool.id) { mutableStateMapOf<String, String>() }
    var additionalCriteria by remember { mutableStateOf("") }

    val glassCardColor = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.6f)
    } else {
        Color.White.copy(alpha = 0.85f)
    }

    ThemeBackground(isDarkTheme = isDarkTheme) {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                ToolDetailTopBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    onBackClick = onBackClick,
                    isDarkTheme = isDarkTheme
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding())
                    .fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = glassCardColor,
                    shadowElevation = 0.dp
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ChatHeaderRow(isDarkTheme)
                        Box(modifier = Modifier.weight(1f)) {
                            if (isInputPanelVisible) {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        BouncingRobotHeader(
                                            description = tool.description,
                                            isDarkTheme = isDarkTheme
                                        )
                                        Spacer(modifier = Modifier.height(24.dp))
                                        HidePanelButton(
                                            isVisible = true,
                                            onToggle = { isInputPanelVisible = false },
                                            isDarkTheme = isDarkTheme
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .verticalScroll(rememberScrollState())
                                            .padding(horizontal = 16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        CommonHeaderSection(
                                            language = selectedLanguage,
                                            onLanguageChange = { selectedLanguage = it },
                                            curriculum = selectedCurriculum,
                                            onCurriculumChange = { selectedCurriculum = it },
                                            isDarkTheme = isDarkTheme
                                        )

                                        HorizontalDivider(
                                            color = if (isDarkTheme) Color(0xFF1F2937) else Color.LightGray,
                                            thickness = 1.dp
                                        )

                                        DynamicBodySection(
                                            inputs = tool.uniqueInputs,
                                            formState = toolFormState,
                                            onFieldChange = { id, valStr -> toolFormState[id] = valStr },
                                            isDarkTheme = isDarkTheme
                                        )

                                        GenericTextField(
                                            label = "Additional Criteria",
                                            placeholder = "Enter any additional requirements or criteria...",
                                            value = additionalCriteria,
                                            onValueChange = { additionalCriteria = it },
                                            isDarkTheme = isDarkTheme,
                                            minLines = 3
                                        )

                                        Button(
                                            onClick = { },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp, bottom = 40.dp),
                                            shape = RoundedCornerShape(12.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF3B82F6)
                                            )
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                        .rotate(-30f)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text("Generate Response")
                                            }
                                        }
                                    }
                                }
                            }

                            else {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(horizontal = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    BouncingRobotHeader(
                                        description = tool.description,
                                        isDarkTheme = isDarkTheme
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 24.dp)
                                        .padding(horizontal = 16.dp)
                                ) {
                                    HidePanelButton(
                                        isVisible = false,
                                        onToggle = { isInputPanelVisible = true },
                                        isDarkTheme = isDarkTheme
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatHeaderRow(isDarkTheme: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF3B82F6).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                painter = painterResource(id = R.drawable.baseline_chat_bubble_24),
                contentDescription = null,
                tint = Color(0xFF60A5FA),
                modifier = Modifier.size(18.dp)
            )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Chat",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }

        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "History",
            tint = if (isDarkTheme) Color.Gray else Color.DarkGray,
            modifier = Modifier
                .size(24.dp)
                .clickable { /* todo History Action */ }
        )
    }
}

@Composable
fun HidePanelButton(
    isVisible: Boolean,
    onToggle: () -> Unit,
    isDarkTheme: Boolean
) {
    val borderColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color(0xFFE5E7EB)
    val contentColor = Color(0xFF3B82F6)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onToggle)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp, alignment = Alignment.CenterHorizontally)
    ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_chat_bubble_24),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = if (isVisible) "Hide Input Panel" else "Show Input Panel",
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )

        Icon(
            imageVector = if (isVisible) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun BouncingRobotHeader(
    description: String,
    isDarkTheme: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "robot_bounce")
    val dy by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dy"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.robot_bot_face),
            contentDescription = "Robot",
            tint = if (isDarkTheme) Color.LightGray else Color.Gray,
            modifier = Modifier
                .size(64.dp)
                .offset(y = dy.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isDarkTheme) Color.Gray else Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}

@Composable
fun ToolDetailTopBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val contentColor = if (isDarkTheme) Color.White else Color.Black

    val toggleContainerColor = if (isDarkTheme) Color(0xFF1F2937) else Color(0xFFE5E7EB)
    val selectedPillColor = if (isDarkTheme) Color(0xFF374151) else Color.White
    val unselectedTextColor = if (isDarkTheme) Color.Gray else Color.DarkGray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.Transparent)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = contentColor
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = toggleContainerColor,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(4.dp)
        ) {
            Row {
                listOf("Chat", "Preview").forEach { tab ->
                    val isSelected = selectedTab == tab

                    val pillColor = if (isSelected) selectedPillColor else Color.Transparent
                    val textColor = if (isSelected) contentColor else unselectedTextColor

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(pillColor)
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.labelMedium,
                            color = textColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = contentColor
            )
        }
    }
}

@Composable
fun CommonHeaderSection(
    language: String,
    onLanguageChange: (String) -> Unit,
    curriculum: String,
    onCurriculumChange: (String) -> Unit,
    isDarkTheme: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        GenericDropdown(
            label = "Language",
            options = listOf("English", "Spanish", "French", "Hindi"),
            selectedValue = language,
            onOptionSelected = onLanguageChange,
            onClear = null,
            isDarkTheme = isDarkTheme
        )

        GenericDropdown(
            label = "Curriculum",
            placeholder = "Select curriculum",
            options = listOf("CBSE", "ICSE", "Common Core", "IGCSE", "IB"),
            selectedValue = curriculum,
            onOptionSelected = onCurriculumChange,
            onClear = { onCurriculumChange("") },
            isDarkTheme = isDarkTheme
        )
    }
}

@Composable
fun DynamicBodySection(
    inputs: List<ToolInputField>,
    formState: Map<String, String>,
    onFieldChange: (String, String) -> Unit,
    isDarkTheme: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        inputs.forEach { field ->
            when (field.type) {
                is InputType.Text -> {
                    GenericTextField(
                        label = field.label,
                        placeholder = field.placeholder,
                        value = formState[field.id] ?: "",
                        onValueChange = { onFieldChange(field.id, it) },
                        isDarkTheme = isDarkTheme
                    )
                }
                is InputType.TextArea -> {
                    GenericTextField(
                        label = field.label,
                        placeholder = field.placeholder,
                        value = formState[field.id] ?: "",
                        onValueChange = { onFieldChange(field.id, it) },
                        isDarkTheme = isDarkTheme,
                        minLines = 4
                    )
                }
                is InputType.Dropdown -> {
                    GenericDropdown(
                        label = field.label,
                        options = (field.type as InputType.Dropdown).options,
                        selectedValue = formState[field.id] ?: "",
                        onOptionSelected = { onFieldChange(field.id, it) },
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

@Composable
fun GenericTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDarkTheme: Boolean,
    minLines: Int = 1
) {
    val containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.DarkGray else Color.LightGray.copy(alpha = 0.6f)
    val placeholderColor = if (isDarkTheme) Color.Gray else Color.Gray

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = if (isDarkTheme) Color.LightGray else Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = placeholder, color = placeholderColor)
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = minLines,
            maxLines = if (minLines > 1) 10 else 1,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                cursorColor = Color(0xFF3B82F6),
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = borderColor
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericDropdown(
    label: String,
    placeholder: String = "Select option",
    options: List<String>,
    selectedValue: String,
    onOptionSelected: (String) -> Unit,
    onClear: (() -> Unit)? = null,
    isDarkTheme: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    val containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.DarkGray else Color.LightGray.copy(alpha = 0.6f)
    val menuBackgroundColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val iconColor = if (isDarkTheme) Color.LightGray else Color.Gray

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = if (isDarkTheme) Color.LightGray else Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(text = placeholder, color = iconColor.copy(alpha = 0.7f))
                },
                trailingIcon = {
                    if (onClear != null && selectedValue.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = iconColor,
                            modifier = Modifier.clickable {
                                onClear()
                            }
                        )
                    } else {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedBorderColor = borderColor
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(menuBackgroundColor)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = textColor) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}