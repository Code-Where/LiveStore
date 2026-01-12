package com.codewhere.livestore.common.extentions

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.shimmerEffect(
    isShimmering: Boolean
): Modifier = if (isShimmering) {
    composed {
        val transition = rememberInfiniteTransition("shimmer_transition")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shimmer_animation"
        )

        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f)
        )

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnimation.value - 200f, 0f),
            end = Offset(
                x = translateAnimation.value,
                y = 0f
            )
        )

        background(brush, shape = RoundedCornerShape(10.dp))
    }
}
else this