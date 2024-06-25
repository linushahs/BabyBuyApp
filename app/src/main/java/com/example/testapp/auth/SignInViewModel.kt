package com.example.testapp.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: AuthResult) {
        if (result.data != null) {
            val user = UserData(
                userId = result.data.userId,
                username = result.data.username,
                email = result.data.email,
                profilePictureUrl = result.data.profilePictureUrl
            )

            _state.update {
                it.copy(
                    isSignInSuccessful = true,
                    signInError = null,
                    user = user
                )
            }
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun getCurrentUser(): UserData? {
        return state.value.user
    }
}