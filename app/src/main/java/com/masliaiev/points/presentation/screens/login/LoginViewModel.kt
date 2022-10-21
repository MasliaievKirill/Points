package com.masliaiev.points.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.usecases.LogInUseCase
import com.masliaiev.points.helpers.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    var navigateToMap = MutableSharedFlow<Unit>()
        private set

    var showErrorToast = MutableSharedFlow<String>()
        private set

    var screenState by mutableStateOf(LoginScreenState())
        private set

    fun logIn(login: String, password: String) {
        viewModelScope.launch {
            when (logInUseCase.logIn(login, password)) {
                is Response.Success -> {
                    navigateToMap.emit(Unit)
                }
                is Response.Error -> {
                    showErrorToast.emit("Some error occured")
                }
            }
        }
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

    fun updateLoginText(text: String) {
        screenState = screenState.copy(loginText = text)
        if (text.isEmpty()) setLoginTextError() else clearLoginTextError()
    }

    fun updatePasswordText(text: String) {
        screenState = screenState.copy(passwordText = text)
        if (text.isEmpty()) setPasswordTextError() else clearPasswordTextError()
    }
}