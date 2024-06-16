package com.example.testapp.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onSignUpResult(result: AuthResult) {
        _state.update { it.copy(
            isSignUpSuccessful = result.data != null,
            signUpError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignUpState() }
    }
}