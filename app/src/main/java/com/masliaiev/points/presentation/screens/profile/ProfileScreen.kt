package com.masliaiev.points.presentation.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.masliaiev.points.presentation.core.components.ContentField
import com.masliaiev.points.presentation.core.components.PointsTopAppBar
import com.masliaiev.points.presentation.core.components.SecondaryButton

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel,
    userLogin: String?,
    onNavigateToLogIn: () -> Unit,
    onNavigateBack: () -> Unit
) {

    val userData = viewModel.userData

    userLogin?.let {
        if (userData == null) viewModel.getUser(it)
    }

    Log.d("LOG_PROFILE", "RECOMPOSED")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White,
        topBar = {
            PointsTopAppBar(
                title = "Profile",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(paddingValues).navigationBarsPadding(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    ContentField(
                        modifier = Modifier.padding(12.dp),
                        description = "Login",
                        text = userData?.login ?: ""
                    )
                    ContentField(
                        modifier = Modifier.padding(12.dp),
                        description = "Email",
                        text = userData?.email ?: ""
                    )
                }
                SecondaryButton(
                    text = "Logout",
                    isEnabled = true,
                    modifier = Modifier.padding(12.dp)
                ) {
                    viewModel.logOut()
                    onNavigateToLogIn()
                }
            }
        }

    }

}