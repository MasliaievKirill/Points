package com.masliaiev.points.presentation.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.points.R
import com.masliaiev.points.helpers.Response
import com.masliaiev.points.presentation.core.components.AppEdittext
import com.masliaiev.points.presentation.core.components.PrimaryButton
import com.masliaiev.points.presentation.core.components.SecondaryButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToAppMap: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val context = LocalContext.current

    val loginText = viewModel.loginText
    val loginTextError = viewModel.loginTextError

    val passwordText = viewModel.passwordText
    val passwordTextError = viewModel.passwordTextError

    val signUpResponse = viewModel.signUpResponse

    Log.d("LOG_LOGIN", viewModel.toString())

    signUpResponse?.let {
        when (it) {
            is Response.Success -> {
                onNavigateToAppMap()
                viewModel.clearResponse()
                Log.d("LOG_LOGIN", "RECOMPOSED")

            }
            is Response.Error -> {
                Toast.makeText(context, it.massage, Toast.LENGTH_SHORT).show()
                viewModel.clearResponse()
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
                    .fillMaxWidth(),
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
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Login",
                        isError = loginTextError
                    ) {
                        viewModel.updateLoginText(it)
                    }
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Password",
                        isError = passwordTextError,
                        isPasswordField = true
                    ) {
                        viewModel.updatePasswordText(it)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    PrimaryButton(
                        text = "Log In",
                        isEnabled = true,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        if (loginText.isNotEmpty() && passwordText.isNotEmpty()) {
                            viewModel.logIn(
                                    login = loginText,
                                    password = passwordText
                            )
                        }
                    }
                    SecondaryButton(
                        text = "Sign Up",
                        isEnabled = true,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        onNavigateToSignUp()
                    }
                }
            }
        }

    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    LoginScreen(navController = null)
//}