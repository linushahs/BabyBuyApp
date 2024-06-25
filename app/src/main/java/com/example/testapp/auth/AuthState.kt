package com.example.testapp.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val user: UserData? = null
)

data class SignUpState(
    val isSignUpSuccessful: Boolean = false,
    val signUpError: String? = null
)
