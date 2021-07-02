package com.example.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private var mUser : FirebaseUser? = null

    private var myNewPassword : String? = null
    private var myRepeatPassword : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)



        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser

        back_arrow_pass.setOnClickListener {
            finish()
        }

        update_password.setOnClickListener {
            myNewPassword = new_password.text.toString().trim()
            myRepeatPassword = new_password.text.toString().trim()

            if(myNewPassword.equals(myRepeatPassword)) {
                mUser!!.updatePassword(myNewPassword!!)
                Toast.makeText(this,"Password updated",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Passwords Don't match",Toast.LENGTH_SHORT).show()
            }
        }
    }
}