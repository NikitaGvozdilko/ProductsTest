package com.productsapp.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.productsapp.NetworkManager
import com.productsapp.ProductsApp
import com.productsapp.R
import com.productsapp.api.model.LoginData
import com.productsapp.utils.AppPref
import kotlinx.android.synthetic.main.activity_login.*

class AuthActivity : AppCompatActivity() {

    private val onAuthCallback: NetworkManager.OnAuthCallback = object :
            NetworkManager.OnAuthCallback {
        override fun onError() {
            progressLoading.visibility = View.GONE
            Toast.makeText(this@AuthActivity, "Authorization error", Toast.LENGTH_LONG).show()
        }

        override fun onAuth(success: Boolean) {
            progressLoading.visibility = View.GONE
            if (success) {
                startActivity(Intent(this@AuthActivity, EditProfileActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
            else Toast.makeText(this@AuthActivity, "Wrong login or password", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (AppPref.getLoginData(this) != null) {
            if (ProductsApp.appDatabase?.profileDao()?.getProfile() == null)
                startEditProfileActivity()
            else startMainActivity()
            return
        }
        buttonLogIn.setOnClickListener {
            if (isDataEntered()) {
                progressLoading.visibility = View.VISIBLE
                ProductsApp.networkManager.login(this@AuthActivity,
                        LoginData(editUsername.text.toString(), editPassword.text.toString()), onAuthCallback)
            }
        }
        textSignIn.setOnClickListener {
            if (isDataEntered()) {
                progressLoading.visibility = View.VISIBLE
                val loginData = LoginData(editUsername.text.toString(), editPassword.text.toString())
                ProductsApp.networkManager.signIn(this@AuthActivity, loginData, onAuthCallback)
            }
        }
        textSignInLater.setOnClickListener{
            startMainActivity()
        }
    }

    private fun startEditProfileActivity() {
        val intent = Intent(this@AuthActivity, EditProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun startMainActivity() {
        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun isDataEntered(): Boolean {
        if (editUsername.text.isEmpty()) {
            editUsername.error = "Enter data"
            return false
        } else if (editPassword.text.isEmpty()) {
            editPassword.error = "Enter data"
            return false
        }
        return true
    }
}
