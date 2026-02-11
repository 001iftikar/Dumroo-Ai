package dumroo.ai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThemeBackground(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val BrandBlue = Color(0xFF3B82F6)
    val BrandOrange = Color(0xFFF97316)

    val backgroundColor = if (isDarkTheme) Color.Black else Color.White

    val blueAlpha = if (isDarkTheme) 0.6f else 0.4f
    val orangeAlpha = if (isDarkTheme) 0.5f else 0.4f

    val bottomBlueAlpha = if (isDarkTheme) 0.4f else 0.2f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BrandBlue.copy(alpha = blueAlpha),
                            BrandBlue.copy(alpha = 0.01f),
                            Color.Transparent
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.5f)
                .height(220.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            BrandOrange.copy(alpha = orangeAlpha),
                            BrandOrange.copy(alpha = 0.01f),
                            Color.Transparent
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.5f)
                .height(220.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            BrandOrange.copy(alpha = orangeAlpha),
                            BrandOrange.copy(alpha = 0.01f),
                            Color.Transparent
                        ),
                        start = Offset(Float.POSITIVE_INFINITY, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.5f)
                .height(150.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            BrandBlue.copy(alpha = bottomBlueAlpha),
                            Color.Transparent
                        ),
                        center = Offset(0f, Float.POSITIVE_INFINITY),
                        radius = 400f
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth(0.5f)
                .height(150.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            BrandBlue.copy(alpha = bottomBlueAlpha),
                            Color.Transparent
                        ),
                        center = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                        radius = 400f
                    )
                )
        )

        content()
    }
}