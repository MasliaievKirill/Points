package com.masliaiev.points.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.usecases.SignUpUseCase
import com.masliaiev.points.helpers.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var signUpResponse by mutableStateOf<Response?>(null)

    var emailText by mutableStateOf("")
    var emailTextError by mutableStateOf(false)

    var loginText by mutableStateOf("")
    var loginTextError by mutableStateOf(false)

    var passwordText by mutableStateOf("")
    var passwordTextError by mutableStateOf(false)

    fun signUp(user: User) {
        viewModelScope.launch {
            signUpResponse = signUpUseCase.signUp(user)
        }
    }

    fun clearResponse() {
        signUpResponse = null
    }

    private fun setEmailTextError() {
        emailTextError = true
    }

    private fun clearEmailTextError() {
        emailTextError = false
    }

    private fun setLoginTextError() {
        loginTextError = true
    }

    private fun clearLoginTextError() {
        loginTextError = false
    }

    private fun setPasswordTextError() {
        passwordTextError = true
    }

    private fun clearPasswordTextError() {
        passwordTextError = false
    }

    fun updateEmailText(text: String) {
        emailText = text
        if (text.isEmpty()) setEmailTextError() else clearEmailTextError()
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