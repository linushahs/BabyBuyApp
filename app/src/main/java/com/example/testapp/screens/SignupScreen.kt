package com.example.testapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.components.CustomPasswordField
import com.example.testapp.components.CustomTextField
import com.example.testapp.components.EmailTextField
import com.example.testapp.components.GoogleSigninButton
import com.example.testapp.components.PasswordRulesContainer
import com.example.testapp.ui.theme.BabyBuyAppTheme
import com.example.testapp.utils.ConfirmPasswordValidationMessage
import com.example.testapp.utils.EmailValidationMessage
import com.example.testapp.utils.PasswordValidationMessage
import com.example.testapp.utils.isValidEmail
import com.example.testapp.utils.isValidPassword

//@Preview(showBackground = true, widthDp = 360, heightDp = 700
@Composable
fun SignupScreenPreview(onSubmit: (email: String, password: String) -> Unit, onLoginBtnClicked: () -> Unit = {}) {
    var formSubmitted by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") };
    var passwordVisible by remember { mutableStateOf(false) }

    var cpassword by remember { mutableStateOf("") }
    var cpasswordVisible by remember { mutableStateOf(false) }

    var emailError: String? by remember { mutableStateOf(null) };
    var passwordError: String? by remember { mutableStateOf(null) };
    var cpasswordError: String? by remember { mutableStateOf(null) };

    val emailBorderColor = {
        if (emailError == null) Color.LightGray else Color.Red
    };

    val passwordBorderColor = {
        if (passwordError == null) Color.LightGray else Color.Red
    }

    val cpasswordBorderColor = {
        if (cpasswordError == null) Color.LightGray else Color.Red
    }

    BabyBuyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 35.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(115.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
                Text(
                    "Welcome to the BabyBuy!",
                    modifier = Modifier
                        .wrapContentWidth(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 18.dp)
            ) {
                Text(
                    "Email",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
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


                Text(
                    "Password",
                    modifier = Modifier.padding(top = 18.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
                CustomPasswordField(
                    input = password,
                    onChange = { input ->
                        password = input;
                        if (formSubmitted) {
                            passwordError = when {
                                password.isBlank() -> PasswordValidationMessage.Required.message
                                !isValidPassword(password) -> PasswordValidationMessage.Invalid.message
                                else -> null
                            }
                        }
                    },
                    placeholder = "*********",
                    passwordVisible = passwordVisible,
                    togglePasswordVisibility = { passwordVisible = !passwordVisible },
                    borderColor = passwordBorderColor()
                )

                if (passwordError != null) {
                    PasswordRulesContainer(password)
                }

// ============= Confirm password field
                Text(
                    "Confirm Password",
                    modifier = Modifier.padding(top = 18.dp),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
                CustomPasswordField(
                    input = cpassword,
                    onChange = { input ->
                        cpassword = input;
                        if (formSubmitted) {
                            cpasswordError = when {
                                cpassword.isBlank() -> ConfirmPasswordValidationMessage.Required.message
                                password != cpassword -> ConfirmPasswordValidationMessage.DoesNotMatch.message
                                else -> null
                            }
                        }
                    },
                    placeholder = "*********",
                    passwordVisible = cpasswordVisible,
                    togglePasswordVisibility = { cpasswordVisible = !cpasswordVisible },
                    borderColor = cpasswordBorderColor()
                )

                if (cpasswordError != null) {
                    Text(
                        text = cpasswordError!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

// ============= Signup button
                Button(
                    onClick = {
                        formSubmitted = true
                        
                        emailError = when {
                            email.isBlank() -> EmailValidationMessage.Required.message
                            !isValidEmail(email) -> EmailValidationMessage.Invalid.message
                            else -> null
                        }

                        passwordError = when {
                            password.isBlank() -> PasswordValidationMessage.Required.message
                            !isValidPassword(password) -> PasswordValidationMessage.Invalid.message
                            else -> null
                        }

                        cpasswordError = when {
                            cpassword.isBlank() -> ConfirmPasswordValidationMessage.Required.message
                            password != cpassword -> ConfirmPasswordValidationMessage.DoesNotMatch.message
                            else -> null
                        }

                        val isValid = emailError == null && passwordError == null && cpasswordError == null;
                        if (isValid) {
                            // Handle successful login
                            onSubmit(email, password);
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp)
                        .height(45.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Signup", style = MaterialTheme.typography.bodySmall)
                }

                GoogleSigninButton()

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Already have an account?",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    Text(
                        "Login",
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .clickable(onClick = onLoginBtnClicked),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}