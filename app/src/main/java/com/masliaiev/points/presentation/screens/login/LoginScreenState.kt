package com.masliaiev.points.presentation.screens.login

data class LoginScreenState(
    val loginText: String = "",
    val loginTextError: Boolean = false,
    val passwordText: String = "",
    val passwordTextError: Boolean = false,
    val navigateToMapKey: Boolean = true,
    val showErrorKey: Boolean = true
)
