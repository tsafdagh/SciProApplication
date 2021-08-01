package com.ecomm.sciproapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.ecomm.sciproapplication.views.SelectProfilActivity.SelectProfilActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainAndroidViewActivity : AppCompatActivity() {

    val providers = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(FirebaseAuth.getInstance().currentUser != null){
            navigateToSelectProfil()
        }else{
            configureOnClickListener()
        }
        lockBackPress()
    }


    private val RC_SIGN_IN = 100

    private fun configureOnClickListener() {
        id_sing_in_phone.setOnClickListener {
            it.isClickable = false

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                     .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            id_sing_in_phone.isClickable = true
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                navigateToSelectProfil()
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                val errorCode = response?.error?.errorCode
                val errorMessage = response?.error?.message

                howError(errorCode, errorMessage)
            }
        }
    }

    private fun navigateToSelectProfil() {
        val intent = Intent(this, SelectProfilActivity::class.java)
        startActivity(intent)
    }

    private fun howError(errorCode: Int?, errorMessage: String?) {
        Snackbar.make(id_main_activity_view,
            "Error: CODE: $errorCode, MESSAGE: $errorMessage",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun lockBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alertDialog = AlertDialog.Builder(this@MainAndroidViewActivity)
                    .setMessage(R.string.do_you_want_to_close_app)
                    .setPositiveButton(R.string.yes) { i, dialog ->
                        //  findNavController().navigateUp()
                        finish()
                    }.setNegativeButton(R.string.no) { i, dialog -> }
                    .create()
                    .show()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }
}