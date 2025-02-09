package com.code.newsapp.presentation.splashscreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedTextView() {

    val infiniteTransition = rememberInfiniteTransition(label = "Infinite transition")

    //fade animation
    val fadeAnimation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "fade"
    )

    //scale animation
    val scale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Scale"
    )

    //Content

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        //Text one fade
        Text(
            text = "Animated",
            modifier = Modifier.alpha(fadeAnimation.value),
            fontSize = 40.sp,
            color = Color.Black
        )

        //Text two scale 
        
        Text(text = "TextView", modifier = Modifier.graphicsLayer {
            scaleX = scale.value
            scaleY = scale.value
            transformOrigin = TransformOrigin.Center
        }, fontSize = 40.sp, color = Color.Red)

    }
}