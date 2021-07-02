package com.example.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var mValidateInput : ValidateInput

    private lateinit var mAuth: FirebaseAuth

    private val loadingAnimation by lazy {
        LoadingAnimation(this)
    }

    private var mEmail : String = ""
    private var mPassword : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance();

        mValidateInput = ValidateInput(this,sign_up_email,sign_up_password,repeat_password)


        back_arrow.setOnClickListener {
            finish()
        }

        sign_up_btn.setOnClickListener {
            signUpNewAccount()
        }


    }

    private fun signUpNewAccount() {

        loadingAnimation.loadingAnimationDialog()

        mEmail = sign_up_email.text.toString().trim()
        mPassword = sign_up_password.text.toString().trim()

        val emailVerified = mValidateInput.validateEmail()
        val passwordVerified = mValidateInput.validatePassword()
        val repeatPasswordVerified = mValidateInput.repeatPasswordValidation()

        if(emailVerified && passwordVerified && repeatPasswordVerified) {

            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SUCCESS : ", "createUserWithEmail:success")
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"Fatal Error " ,Toast.LENGTH_SHORT).show()
                        loadingAnimation.dismissLoadingAnimation()
                    }
                }
        } else {
            loadingAnimation.dismissLoadingAnimation()
        }
    }


}
