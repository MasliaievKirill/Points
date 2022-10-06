package com.masliaiev.points.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.usecases.LogInUseCase
import com.masliaiev.points.helpers.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    var signUpResponse by mutableStateOf<Response?>(null)

    var loginText by mutableStateOf("")
    var loginTextError by mutableStateOf(false)

    var passwordText by mutableStateOf("")
    var passwordTextError by mutableStateOf(false)

    fun logIn(login: String, password: String) {
        viewModelScope.launch {
            signUpResponse = logInUseCase.logIn(login, password)
        }
    }

    fun clearResponse () {
        signUpResponse = null
    }

    private fun setLoginTextError () {
        loginTextError = true
    }

    private fun clearLoginTextError () {
        loginTextError = false
    }

    private fun setPasswordTextError () {
        passwordTextError = true
    }

    private fun clearPasswordTextError () {
        passwordTextError = false
    }

    fun updateLoginText(text: String) {
        loginText = text
        if (text.isEmpty()) setLoginTextError() else clearLoginTextError()
    }

    fun updatePasswordText(text: String) {
        passwordText = text
        if (text.isEmpty()) setPasswordTextError() else clearPasswordTextError()
    }
}