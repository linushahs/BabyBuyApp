package com.example.testapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.auth.FirebaseAuthClient
import com.example.testapp.auth.SignInViewModel
import com.example.testapp.auth.SignUpViewModel
import com.example.testapp.screens.AddItemScreen
import com.example.testapp.screens.DashboardScreen
import com.example.testapp.screens.GetStartedScreenPreview
import com.example.testapp.screens.ItemDetailsScreen
import com.example.testapp.screens.LoginScreenPreview
import com.example.testapp.screens.SignupScreenPreview
import com.example.testapp.utils.addItemToDb
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

// Routes of the app (enums)
enum class BabyBuyScreen() {
    Login,
    Signup,
    Dashboard,
    GetStarted,
    AddItem,
    ItemDetails
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BabyBuyApp(
    googleAuthUiClient: FirebaseAuthClient,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    db: FirebaseFirestore
) {
    val navController = rememberNavController();

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold() { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = BabyBuyScreen.GetStarted.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = BabyBuyScreen.GetStarted.name) {
                    GetStartedScreenPreview(onGetStartedClicked = {
                        navController.navigate(
                            BabyBuyScreen.Login.name
                        )
                    })
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
                    DashboardScreen(
                        onAddItemClick = { navController.navigate(BabyBuyScreen.AddItem.name) },
                        googleAuthUiClient,
                        db
                    )
                }

                composable(route = BabyBuyScreen.AddItem.name) {
                    AddItemScreen(onAddItemClick = { item ->
                        lifecycleOwner.lifecycleScope.launch {
                            val user = googleAuthUiClient.getSignedInUser();
                            if (user?.email != null) {
                                addItemToDb(db, context, item, user.email)
                            }

                            navController.navigate(BabyBuyScreen.Dashboard.name)
                        }
                    }, onBackBtnClick = {
                        navController.navigate(BabyBuyScreen.Dashboard.name)
                    })
                }

                composable(route = BabyBuyScreen.ItemDetails.name) {
                    ItemDetailsScreen(onBackBtnClick = { navController.navigate(BabyBuyScreen.Dashboard.name) })
                }
            }
        }
    }
}

