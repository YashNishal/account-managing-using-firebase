package com.example.signin

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast

internal class ValidateInput {
    private var context: Context
    private var email: EditText
    private var password: EditText? = null
    private var repeatPassword: EditText? = null
    private var emailInput: String? = null
    private var passwordInput: String? = null
    private var repeatPasswordInput: String? = null


    constructor(myContext: Context, myEmail: EditText) {
        context = myContext
        email = myEmail
    }

    constructor(myContext: Context, myEmail: EditText, myPassword: EditText?) {
        context = myContext
        email = myEmail
        password = myPassword
    }

    constructor(myContext: Context, myEmail: EditText, myPassword: EditText?, myRepeatPassword: EditText?
    ) {
        context = myContext
        email = myEmail
        password = myPassword
        repeatPassword = myRepeatPassword
    }

    fun validateEmail(): Boolean {
        emailInput = email.text.toString().trim { it <= ' ' }
        return if (emailInput!!.isEmpty()) {
            Toast.makeText(context, "Please enter your Email Address.", Toast.LENGTH_SHORT).show()
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.toString()).matches()) {
            Toast.makeText(context, "Invalid Email Address.", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun validatePassword(): Boolean {
        passwordInput = password!!.text.toString().trim { it <= ' ' }
        return when {
            passwordInput!!.isEmpty() -> {
                Toast.makeText(context, "Please enter your Password.", Toast.LENGTH_SHORT).show()
                false
            }
            passwordInput!!.length < 8 -> {
                Toast.makeText(context, "Password too Short.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }

    fun repeatPasswordValidation(): Boolean {
        repeatPasswordInput = repeatPassword?.text.toString().trim { it <= ' ' }
        return when {
            repeatPasswordInput!!.isEmpty() -> {
                Toast.makeText(context, "Fill out all fields.", Toast.LENGTH_SHORT).show()
                false
            }
            repeatPasswordInput != passwordInput -> {
                Toast.makeText(context, "Passwords Don't match.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }
}