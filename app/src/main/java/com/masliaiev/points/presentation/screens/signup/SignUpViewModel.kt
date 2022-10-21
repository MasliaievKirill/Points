package com.masliaiev.points.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.usecases.SignUpUseCase
import com.masliaiev.points.helpers.Response
import com.masliaiev.points.presentation.screens.login.LoginScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var navigateToMap = MutableSharedFlow<Unit>()
        private set

    var showErrorToast = MutableSharedFlow<String>()
        private set

    var screenState by mutableStateOf(SignUpScreenState())
        private set

    fun signUp(user: User) {
        viewModelScope.launch {
            when (signUpUseCase.signUp(user)) {
                is Response.Success -> {
                    navigateToMap.emit(Unit)
                }
                is Response.Error -> {
                    showErrorToast.emit("Some error occured")
                }
            }
        }
    }

    private fun setEmailTextError() {
        screenState = screenState.copy(emailTextError = true)
    }

    private fun clearEmailTextError() {
        screenState = screenState.copy(emailTextError = false)
    }

    private fun setLoginTextError() {
        screenState = screenState.copy(loginTextError = true)
    }

    private fun clearLoginTextError() {
        screenState = screenState.copy(loginTextError = false)
    }

    private fun setPasswordTextError() {
        screenState = screenState.copy(passwordTextError = true)
    }

    private fun clearPasswordTextError() {
        screenState = screenState.copy(passwordTextError = false)
    }

    fun updateEmailText(text: String) {
        screenState = screenState.copy(emailText = text)
        if (text.isEmpty()) setEmailTextError() else clearEmailTextError()
    }

    fun updateLoginText(text: String) {
        screenState = screenState.copy(loginText = text)
        if (text.isEmpty()) setLoginTextError() else clearLoginTextError()
    }

    fun updatePasswordText(text: String) {
        screenState = screenState.copy(passwordText = text)
        if (text.isEmpty()) setPasswordTextError() else clearPasswordTextError()
    }
}