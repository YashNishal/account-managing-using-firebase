package com.example.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_update_email.*

class UpdateEmailActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private var mUser : FirebaseUser? = null

    private lateinit var mValidateInput: ValidateInput

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser

        mValidateInput = ValidateInput(this,new_email)

        setCurrentEmail()

        back_arrow_email.setOnClickListener {
            finish()
        }

        update_email.setOnClickListener {
            updateEmail()
        }

        send_verification_email_txt.setOnClickListener {
            if(mUser!!.isEmailVerified ) {
                Toast.makeText(this, "Email already verified", Toast.LENGTH_SHORT).show()
            } else {
                mUser!!.sendEmailVerification()
                Toast.makeText(this, "Verification email sent!", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun setCurrentEmail() {
        if(mUser != null) {
            current_email.isEnabled = true
            current_email.setText(mUser!!.email)
            current_email.isEnabled = false
        }else {
            Toast.makeText(this,"Please login to continue",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEmail() {
        val emailVerified = mValidateInput.validateEmail()
        if(emailVerified && mUser != null) {
            val myNewEmail = new_email.text.toString()
            mUser!!.updateEmail(myNewEmail)
            Toast.makeText(this,"Email address updated successfully.",Toast.LENGTH_SHORT).show()
            //finish()
            Handler().postDelayed({
                setCurrentEmail()
            }, 2000)

        } else {
            Toast.makeText(this,"Invalid email address",Toast.LENGTH_SHORT).show()
        }
    }
}