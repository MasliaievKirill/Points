package com.masliaiev.points.presentation.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.points.domain.entity.User
import com.masliaiev.points.domain.usecases.GetUserUseCase
import com.masliaiev.points.domain.usecases.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var userData by mutableStateOf<User?>(null)

    fun getUser(userLogin: String) {
        viewModelScope.launch {
            userData = getUserUseCase.getUser(userLogin)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.logOut()
        }
    }
}