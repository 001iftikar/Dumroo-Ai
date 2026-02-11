package dumroo.ai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dumroo.ai.domain.model.Tool

@Composable
fun ToolItem(
    modifier: Modifier = Modifier,
    tool: Tool,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isDarkTheme) Color.Black else Color.White
    val borderColor = if (isDarkTheme) Color.White.copy(alpha = 0.15f) else Color.LightGray.copy(alpha = 0.5f)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val iconBoxColor = if (isDarkTheme) Color.White else Color(0xFFF3F4F6)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBoxColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = tool.icon),
                contentDescription = null,
                tint = tool.tint,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = tool.title,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}




















