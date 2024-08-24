package com.example.stopwatch

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stopwatch.service.StopwatchService


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(stopwatchService: StopwatchService) {

    val context = LocalContext.current
    val hours by stopwatchService.hours
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds
    val currentState by stopwatchService.currentState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.weight(weight = 9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedContent(targetState = hours, transitionSpec = { addAnimation() },
                label = ""
            ) { targetState ->
                Text(
                    text = targetState,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if(hours=="00") Color.White else Color.Blue
                    )
                )
            }


        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int=600): ContentTransform {
    return (slideInVertically(animationSpec = tween(durationMillis = duration)) { fullHeight -> fullHeight
    } + fadeIn(animationSpec = tween(durationMillis = duration))).togetherWith(
        slideOutVertically(
            animationSpec = tween(durationMillis = duration)
        ) { fullHeight -> fullHeight } + fadeOut(
            animationSpec = tween(durationMillis = duration)
        ))
}