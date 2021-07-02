package com.example.signin

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog


class LoadingAnimation(private val activity: Activity) {
    private lateinit var dialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder


    fun loadingAnimationDialog() {
        builder = AlertDialog.Builder(activity)
        builder.setView(LayoutInflater.from(activity).inflate(R.layout.loading,null))
        builder.setCancelable(false)
        dialog = builder.show()
    }
    fun dismissLoadingAnimation() {
        dialog.dismiss()
    }
}