package com.example.testapp.utils

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

data class SignUpState(
    val isSignUpSuccessful: Boolean = false,
    val signUpError: String? = null
)
