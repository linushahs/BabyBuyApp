package com.example.testapp.utils;

import java.util.regex.Pattern


fun isValidPassword(password: String): Boolean {
    val passwordREGEX = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
//            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 8 characters
            "$");
    return passwordREGEX.matcher(password).matches()
}

fun isValidEmail(email: String): Boolean {
    // Implement your email validation logic here
    // You can use regular expressions or any other method
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

enum class PasswordValidationMessage(val message: String) {
    Required("Password is required."),
    Invalid("Invalid password"),
    AtLeastOneNumeric("At least one numeric character."),
    AtLeastOneLowercase("At least one lowercase letter."),
    AtLeastOneUppercase("At least one uppercase letter."),
    NoWhitespace("Non-whitespace characters."),
    MinLength("At least 6 characters long.");
}

enum class EmailValidationMessage(val message: String){
    Required("This field is required"),
    Invalid("Invalid email addresss")
}

enum class ConfirmPasswordValidationMessage(val message: String){
    Required("This field is required"),
    DoesNotMatch("Password doesn't match")
}