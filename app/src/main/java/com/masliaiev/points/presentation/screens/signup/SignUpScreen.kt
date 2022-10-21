package com.masliaiev.points.presentation.screens.signup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.points.R
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.presentation.core.components.AppEdittext
import com.masliaiev.points.presentation.core.components.PrimaryButton
import com.masliaiev.points.presentation.core.components.SecondaryButton

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToAppMap: () -> Unit,
    onNavigateToLogIn: () -> Unit
) {
    val context = LocalContext.current
    val screenState = viewModel.screenState


    LaunchedEffect(screenState.navigateToMapKey) {
        Log.d("LOG_LOGIN", "LaunchedEffect")
        with(viewModel) {
            navigateToMap.collect {
                onNavigateToAppMap()
            }
        }
    }

    LaunchedEffect(screenState.showErrorKey) {
        Log.d("LOG_LOGIN", "LaunchedEffect")
        with(viewModel) {
            showErrorToast.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.35f)
                    .fillMaxWidth()
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Points", fontSize = 48.sp)
                Image(
                    painter = painterResource(id = R.drawable.ic_map_pin),
                    contentDescription = "Logo"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Email",
                        isError = screenState.emailTextError,
                        initialText = screenState.emailText
                    ) {
                        viewModel.updateEmailText(it)
                    }
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Login",
                        isError = screenState.loginTextError,
                        initialText = screenState.loginText
                    ) {
                        viewModel.updateLoginText(it)
                    }
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Password",
                        isError = screenState.passwordTextError,
                        isPasswordField = true,
                        initialText = screenState.passwordText
                    ) {
                        viewModel.updatePasswordText(it)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    PrimaryButton(
                        text = "Sign Up",
                        isEnabled = true,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        if (screenState.emailText.isNotEmpty() &&
                            screenState.loginText.isNotEmpty() &&
                            screenState.passwordText.isNotEmpty()
                        ) {
                            viewModel.signUp(
                                User(
                                    login = screenState.loginText,
                                    email = screenState.emailText,
                                    password = screenState.passwordText
                                )
                            )
                        }
                    }
                    SecondaryButton(
                        text = "Log In",
                        isEnabled = true,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        onNavigateToLogIn()
                    }
                }
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SignUpScreen(navController = null)
//}