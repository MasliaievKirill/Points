package com.masliaiev.points.presentation.screens.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.points.R
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.helpers.Response
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

    val emailText = viewModel.emailText
    val emailError = viewModel.emailTextError

    val loginText = viewModel.loginText
    val loginError = viewModel.loginTextError

    val passwordText = viewModel.passwordText
    val passwordError = viewModel.passwordTextError

    val signUpResponse = viewModel.signUpResponse

    signUpResponse?.let {
        when (it) {
            is Response.Success -> {
                onNavigateToAppMap()
                viewModel.clearResponse()
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Email",
                        isError = emailError
                    ) {
                        viewModel.updateEmailText(it)
                    }
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Login",
                        isError = loginError
                    ) {
                        viewModel.updateLoginText(it)
                    }
                    AppEdittext(
                        modifier = Modifier.padding(12.dp),
                        hint = "Password",
                        isError = passwordError,
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
                        text = "Sign Up",
                        isEnabled = true,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        if (emailText.isNotEmpty() && loginText.isNotEmpty() && passwordText.isNotEmpty()) {
                            viewModel.signUp(
                                User(
                                    login = loginText,
                                    email = emailText,
                                    password = passwordText
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