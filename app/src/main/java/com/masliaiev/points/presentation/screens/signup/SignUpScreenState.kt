package com.masliaiev.points.presentation.screens.signup

data class SignUpScreenState(
    val emailText: String = "",
    val emailTextError: Boolean = false,
    val loginText: String = "",
    val loginTextError: Boolean = false,
    val passwordText: String = "",
    val passwordTextError: Boolean = false,
    val navigateToMapKey: Boolean = true,
    val showErrorKey: Boolean = true
)
