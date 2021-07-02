package com.example.signin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private var mUser : FirebaseUser? = null

    private var myEmail : String? = null
    private var myId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialisingText()

        update_email_btn.setOnClickListener {
            startActivity(Intent(this, UpdateEmailActivity::class.java))
        }

        update_password_btn.setOnClickListener {
            startActivity(Intent(this,UpdatePasswordActivity::class.java))
        }

        logout_btn.setOnClickListener {
            logOut()
        }
    }

    override fun onResume() {
        super.onResume()
        initialisingText()
    }

    @Override
    override fun onBackPressed() {
        logOut()
    }

    private fun logOut() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Logout?")
            setMessage("Are you sure you want to Logout?")
            setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                mAuth.signOut()
                finish()
            })
            setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })

            show()
        }
    }

    private fun initialisingText() {
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser

        if(mUser != null)  {
            myEmail = mUser!!.email
            myId = mUser!!.uid
            email_address_txt.text = myEmail
            id_txt.text = myId
        }
        checkAccountVerification()
    }

    private fun checkAccountVerification() {
        val isVerified = mUser!!.isEmailVerified
        if (isVerified) {
            verified_account_txt.text = "Account verified"
            verified_account_txt.setTextColor(ContextCompat.getColor(this,R.color.green))
        }
    }
}