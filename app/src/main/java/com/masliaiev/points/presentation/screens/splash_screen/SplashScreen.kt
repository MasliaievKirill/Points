package com.masliaiev.points.presentation.screens.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.masliaiev.points.R

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel,
    onNavigateToAppMap: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {

    val userIsSignedIn = viewModel.userIsSignedIn

    userIsSignedIn?.let {
        when (it) {
            true -> {
                onNavigateToAppMap()
                viewModel.clearResponse()
            }
            false -> {
                onNavigateToLogIn()
                viewModel.clearResponse()
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Points", fontSize = 48.sp)
            Image(
                painter = painterResource(id = R.drawable.ic_map_pin),
                contentDescription = "Logo"
            )
        }
    }
}