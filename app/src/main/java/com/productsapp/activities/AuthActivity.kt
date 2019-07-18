package com.productsapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            Toast.makeText(this@AuthActivity, "Error", Toast.LENGTH_LONG).show()
        }

        override fun onAuth(success: Boolean) {
            if (success) startActivity(Intent(this@AuthActivity, MainActivity::class.java))
            else Toast.makeText(this@AuthActivity, "Wrong password", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(AppPref.getLoginData(this) != null)  onAuthCallback.onAuth(true)
        buttonSignIn.setOnClickListener {
            val loginData = LoginData(editUsername.text.toString(), editPassword.text.toString())
            ProductsApp.networkManager.signIn(this@AuthActivity, loginData, onAuthCallback)
        }
    }
}
