package dumroo.ai.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TopBarIcon(
    icon: Int,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.size(34.dp),
        onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
        )
    }
}