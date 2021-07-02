package com.example.signin

import android.R.attr.password
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class MainActivity : AppCompatActivity() {

    private lateinit var mValidateInput: ValidateInput

    private lateinit var mAuth: FirebaseAuth

    private val loadingAnimation by lazy {
        LoadingAnimation(this)
    }

    private var mEmail: String = ""
    private var mPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        create_account_txt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        sign_in_btn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        mAuth = FirebaseAuth.getInstance();


        mValidateInput = ValidateInput(this, sign_in_email, sign_in_password)

        sign_in_btn.setOnClickListener {
            signIn()
        }
    }

    @Override
    override fun onStart() {
        super.onStart()

        val mUser : FirebaseUser? = mAuth.currentUser

        if(mUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    public fun signIn() {

        loadingAnimation.loadingAnimationDialog()

        val emailVerified = mValidateInput.validateEmail()
        val passwordVerified = mValidateInput.validatePassword()

        if (emailVerified && passwordVerified) {

            mEmail = sign_in_email.text.toString().trim()
            mPassword = sign_in_password.text.toString().trim()

            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(this, HomeActivity::class.java))
                        loadingAnimation.dismissLoadingAnimation()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Email/Password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                        loadingAnimation.dismissLoadingAnimation()
                    }
                }
        } else {
            loadingAnimation.dismissLoadingAnimation()
        }
    }



}