package com.masliaiev.points.presentation.screens.splash_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.usecases.CheckSignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor (
    private val checkSignInUseCase: CheckSignInUseCase
) : ViewModel() {

    var userIsSignedIn by mutableStateOf<Boolean?>(null)

    init {
        checkSignIn()
    }

    private fun checkSignIn() {
        viewModelScope.launch (Dispatchers.IO) {
            delay(1000)
            userIsSignedIn = checkSignInUseCase.checkUserSignedIn()
        }
    }

    fun clearResponse () {
        userIsSignedIn = null
    }
}