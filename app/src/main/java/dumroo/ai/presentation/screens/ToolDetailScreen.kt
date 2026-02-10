package dumroo.ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dumroo.ai.domain.model.Tool
import dumroo.ai.domain.model.ToolInputField
import dumroo.ai.presentation.components.ThemeBackground

@Composable
fun ToolDetailScreen(
    tool: Tool,
    isDarkTheme: Boolean,
    onBackClick: () -> Unit
) {
    ThemeBackground(
        isDarkTheme = isDarkTheme
    ) {
        // 1. Common State (Header)
        var selectedLanguage by remember { mutableStateOf("English") }
        var selectedCurriculum by remember { mutableStateOf("ICSE") }

        // 2. Dynamic State (Body)
        val toolFormState = remember(tool.id) { mutableStateMapOf<String, String>() }

        // 3. Common State (Footer)
        var additionalCriteria by remember { mutableStateOf("") }

        Scaffold(
            containerColor = if (isDarkTheme) Color.Black else Color(0xFFF9FAFB),
            topBar = {
                // Add your TopBar here (Back button, Title, etc.)
                // For now, just a placeholder or the one you used before
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // --- SECTION 1: GLOBAL SETTINGS ---
                Text(
                    text = "Global Settings",
                    color = if (isDarkTheme) Color.Gray else Color.DarkGray,
                    style = MaterialTheme.typography.labelMedium
                )

                CommonHeaderSection(
                    language = selectedLanguage,
                    onLanguageChange = { selectedLanguage = it },
                    curriculum = selectedCurriculum,
                    onCurriculumChange = { selectedCurriculum = it },
                    isDarkTheme = isDarkTheme
                )

                HorizontalDivider(
                    color = if (isDarkTheme) Color.DarkGray else Color.LightGray,
                    thickness = 0.5.dp
                )

                // --- SECTION 2: DYNAMIC INPUTS ---
                Text(
                    text = tool.title,
                    color = if (isDarkTheme) Color.White else Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                DynamicBodySection(
                    inputs = tool.uniqueInputs,
                    formState = toolFormState,
                    onFieldChange = { id, value -> toolFormState[id] = value },
                    isDarkTheme = isDarkTheme
                )

                // --- SECTION 3: COMMON FOOTER (Additional Criteria) ---
                // This appears in ALL tools as seen in your screenshots
                GenericTextField(
                    label = "Additional Criteria",
                    placeholder = "Enter any additional requirements or criteria...",
                    value = additionalCriteria,
                    onValueChange = { additionalCriteria = it },
                    isDarkTheme = isDarkTheme,
                    minLines = 3 // It's usually a bit taller
                )

                // --- SECTION 4: GENERATE BUTTON ---
                Button(
                    onClick = {
                        val finalData = mapOf(
                            "language" to selectedLanguage,
                            "curriculum" to selectedCurriculum,
                            "additional_criteria" to additionalCriteria
                        ) + toolFormState

                        println("Generating with: $finalData")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6)
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // The Send Icon
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send, // Uses "Send" icon
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(-30f) // <--- Tilts 30 degrees upward
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

                        Text(
                            text = "Generate Response",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
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

        // 1. Language: Standard Dropdown (No Clear Option)
        GenericDropdown(
            label = "Language",
            options = listOf("English", "Spanish", "French", "Hindi"),
            selectedValue = language,
            onOptionSelected = onLanguageChange,
            onClear = null, // <--- Keeps the arrow visible always
            isDarkTheme = isDarkTheme
        )

        // 2. Curriculum: Clearable Dropdown (Cross appears when selected)
        GenericDropdown(
            label = "Curriculum",
            placeholder = "Select curriculum",
            options = listOf("CBSE", "ICSE", "Common Core", "IGCSE", "IB"),
            selectedValue = curriculum,
            onOptionSelected = onCurriculumChange,
            onClear = { onCurriculumChange("") }, // <--- Enables the 'X' icon
            isDarkTheme = isDarkTheme
        )
    }
}

@Composable
fun DynamicBodySection(
    inputs: List<ToolInputField>,
    formState: Map<String, String>, // The answers
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
                        minLines = 4 // Taller for TextArea
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
    minLines: Int = 1 // Default to 1 line, set to >1 for Text Area
) {
    // 1. Theme Colors
    val containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.DarkGray else Color.LightGray.copy(alpha = 0.6f)
    val placeholderColor = if (isDarkTheme) Color.Gray else Color.Gray

    Column(modifier = Modifier.fillMaxWidth()) {
        // Label above the field (Optional, but looks cleaner like the screenshot)
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
            minLines = minLines, // Handles Text vs TextArea
            maxLines = if (minLines > 1) 10 else 1,
            shape = RoundedCornerShape(12.dp), // Rounded corners
            colors = OutlinedTextFieldDefaults.colors(
                // Text Colors
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                cursorColor = Color(0xFF3B82F6), // Brand Blue

                // Container Colors
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,

                // Border Colors
                focusedBorderColor = Color(0xFF3B82F6), // Blue when active
                unfocusedBorderColor = borderColor // Gray when inactive
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
    onClear: (() -> Unit)? = null, // <--- Nullable! If null, no 'X' icon appears.
    isDarkTheme: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    // Theme Colors
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
                onValueChange = {}, // Read-only
                readOnly = true,
                placeholder = {
                    Text(text = placeholder, color = iconColor.copy(alpha = 0.7f))
                },
                trailingIcon = {
                    // LOGIC: Only show 'X' if onClear is provided AND we have a value
                    if (onClear != null && selectedValue.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = iconColor,
                            modifier = Modifier.clickable {
                                onClear() // Call the clear action
                                // Do not expand menu
                            }
                        )
                    } else {
                        // Otherwise (Language dropdown OR Empty Curriculum), show Arrow
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