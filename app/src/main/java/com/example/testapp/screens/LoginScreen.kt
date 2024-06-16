package com.example.testapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.components.CustomPasswordField
import com.example.testapp.components.EmailTextField
import com.example.testapp.components.GoogleSigninButton
import com.example.testapp.ui.theme.BabyBuyAppTheme
import com.example.testapp.ui.theme.BorderPrimaryColor
import com.example.testapp.ui.theme.PrimaryColor
import com.example.testapp.utils.EmailValidationMessage
import com.example.testapp.utils.PasswordValidationMessage
import com.example.testapp.utils.SignInState
import com.example.testapp.utils.isValidEmail

//@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun LoginScreenPreview(
    state: SignInState = SignInState(),
    onSignInClick: (email: String, password: String) -> Unit,
    onGoogleSignInClick: () -> Unit = {},
    onSignupBtnClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    var formSubmitted by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") };
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError: String? by remember { mutableStateOf(null) };
    var passwordError: String? by remember { mutableStateOf(null) };

    val emailBorderColor = {
        if (emailError == null) BorderPrimaryColor else Color.Red
    };

    val passwordBorderColor = {
        if (passwordError == null) BorderPrimaryColor else Color.Red
    }

    BabyBuyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 25.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                EmailTextField(
                    input = email,
                    onChange = { input ->
                        email = input;
                        if (formSubmitted) {
                            emailError = when {
                                email.isBlank() -> EmailValidationMessage.Required.message
                                !isValidEmail(email) -> EmailValidationMessage.Invalid.message
                                else -> null
                            }
                        }
                    },
                    placeholder = "johndoe@gmail.com",
                    borderColor = emailBorderColor()
                )

                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                CustomPasswordField(
                    input = password,
                    onChange = { input ->
                        password = input;
                        if (formSubmitted) {
                            passwordError = when {
                                password.isBlank() -> PasswordValidationMessage.Required.message
                                else -> null
                            }
                        }
                    },
                    placeholder = "*********",
                    passwordVisible = passwordVisible,
                    togglePasswordVisibility = { passwordVisible = !passwordVisible },
                    borderColor = passwordBorderColor(),
                    modifier = Modifier.padding(top = 20.dp)
                )

                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }

            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxHeight()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = {},
                            modifier = Modifier.padding(0.dp)
                        )

                        Text(
                            "Remember me",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }

                    Text(
                        "Forgot Password?",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryColor,
                        fontWeight = FontWeight.Medium,
                    )
                }
//              ==================== Login button
//              =================================
                Button(
                    onClick = {
                        formSubmitted = true;

                        emailError = when {
                            email.isBlank() -> EmailValidationMessage.Required.message
                            !isValidEmail(email) -> EmailValidationMessage.Invalid.message
                            else -> null
                        }

                        passwordError = when {
                            password.isBlank() -> PasswordValidationMessage.Required.message
                            else -> null
                        }

                        val isValid = emailError == null && passwordError == null;
                        if (isValid) {
                            // Handle successful login
                            onSignInClick(email, password);
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .height(45.dp),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Login", style = MaterialTheme.typography.bodySmall)
                }

//                Google sign in button =====================
//                ===========================================
                GoogleSigninButton(onBtnClicked = onGoogleSignInClick)

                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Don't have an account?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        "Signup",
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .clickable(onClick = onSignupBtnClicked),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}