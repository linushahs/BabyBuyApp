package com.example.testapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.screens.DashboardScreenPreview
import com.example.testapp.screens.GetStartedScreenPreview
import com.example.testapp.screens.LoginScreenPreview
import com.example.testapp.screens.SignupScreenPreview
import com.example.testapp.ui.theme.BabyBuyAppTheme
import com.example.testapp.utils.FirebaseAuthClient
import com.example.testapp.utils.AuthResult
import com.example.testapp.utils.SignInViewModel
import com.example.testapp.utils.SignUpViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        FirebaseAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabyBuyAppTheme {
                BabyBuyApp(
                    googleAuthUiClient,
                    lifecycleOwner = this, // Pass the Activity instance as LifecycleOwner
                    context = applicationContext // Pass the applicationContext
                 )
            }
        }
    }
}

// Routes of the app (enums)
enum class BabyBuyScreen() {
    Login,
    Signup,
    Dashboard,
    GetStarted
}

@Composable
fun BabyBuyApp(googleAuthUiClient: FirebaseAuthClient, lifecycleOwner: LifecycleOwner, context: Context) {
    val navController = rememberNavController();

    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BabyBuyScreen.GetStarted.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BabyBuyScreen.GetStarted.name) {
                GetStartedScreenPreview(onGetStartedClicked = { navController.navigate(BabyBuyScreen.Login.name) })
            }

            composable(route = BabyBuyScreen.Login.name) {
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if (googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate(BabyBuyScreen.Dashboard.name)
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                            lifecycleOwner.lifecycleScope.launch {
                                val signInResult = googleAuthUiClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if (state.isSignInSuccessful) {
                        Toast.makeText(
                            context,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(BabyBuyScreen.Dashboard.name)
                        viewModel.resetState()
                    }
                }

                LoginScreenPreview(
                    state = state,
                    onSignInClick = { email, password ->
                        lifecycleOwner.lifecycleScope.launch {
                            val result =
                                googleAuthUiClient.signInWithEmailAndPassword(email, password)
                            viewModel.onSignInResult(result);
                        }
                    },
                    onGoogleSignInClick = {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    onSignupBtnClicked = { navController.navigate(BabyBuyScreen.Signup.name) },
                )
            }

            composable(route = BabyBuyScreen.Signup.name) {
                val viewModel = viewModel<SignUpViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = state.isSignUpSuccessful) {
                    if (state.isSignUpSuccessful) {
                        Toast.makeText(
                            context,
                            "Created user successfully",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate(BabyBuyScreen.Login.name)
                        viewModel.resetState()
                    }
                }

                SignupScreenPreview(
                    onSubmit = { email, password ->
                        lifecycleOwner.lifecycleScope.launch {
                            val result =
                                googleAuthUiClient.signUpWithEmailAndPassword(email, password)
                            viewModel.onSignUpResult(result);
                        }
                    },
                    onLoginBtnClicked = { navController.navigate(BabyBuyScreen.Login.name) })
            }

            composable(route = BabyBuyScreen.Dashboard.name) {
                DashboardScreenPreview()
            }
        }
    }
}

