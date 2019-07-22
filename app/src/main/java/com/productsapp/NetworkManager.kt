package com.productsapp

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.productsapp.api.LoginApi
import com.productsapp.api.ProductsApi
import com.productsapp.api.model.*
import com.productsapp.utils.AppPref
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {
    private val TIMEOUT_SEC: Long = 15L
    private val TAG = "NetworkManager"
    private var productApi: ProductsApi? = null
    private var loginApi: LoginApi? = null

    constructor() {
        createApi()
    }

    private fun createApi() {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)

        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

        val baseUrl = "http://smktesting.herokuapp.com"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productApi = retrofit.create(ProductsApi::class.java)
        loginApi = retrofit.create(LoginApi::class.java)
    }

    fun login(context: Context, loginData: LoginData, onAuthCallback: OnAuthCallback) {
        loginApi?.login(loginData)?.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.i(TAG, response.toString())
                val authResponse = response.body()
                if(authResponse != null) {
                    if (authResponse.success) {
                        AppPref.saveLoginData(context, loginData, authResponse.token)
                    }
                    onAuthCallback.onAuth(authResponse.success)
                } else onAuthCallback.onError()
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                onAuthCallback.onError()
            }



        })
    }

    fun signIn(context: Context, loginData: LoginData, onAuthCallback: OnAuthCallback) {
        loginApi?.signIn(loginData)?.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.i(TAG, response.toString())
                val authResponse = response.body()
                if(authResponse != null) {
                    if (authResponse.success) {
                        AppPref.saveLoginData(context, loginData, authResponse.token)
                        onAuthCallback.onAuth(authResponse.success)
                    }
                } else onAuthCallback.onError()
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                onAuthCallback.onError()
            }
        })
    }

    fun getProducts(token: String?, callback: OnProductsCallback) {
        productApi?.getProducts(token)?.enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.w(TAG, t.message)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.i(TAG, response.toString())
                val list = response.body()
                if (list != null) {
                    var counter = 1
                    for (product in list) {
                        product.image = "img$counter.png"
                        counter++
                    }
                }
                callback.onSuccess(response.body())
            }

        })
    }

    fun getComments(token: String?, products: List<String>, onCommentsCallback: OnCommentsCallback) {
        for (product in products) {
            productApi?.getComments(token, product)?.enqueue(object : Callback<List<Comment>> {

                override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                    Log.i(TAG, response.toString())
                    if(response.body() != null)
                        onCommentsCallback.onSuccess(response.body()!!)
                }
                override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    Log.w(TAG, t.message)
                    onCommentsCallback.onError()
                }



            })
        }
    }

    fun sendComment(token: String, productId: String, comment: CommentToSend, onCommentsCallback: OnCommentSentCallback) {
        productApi?.sendComment("Token $token", productId, comment)?.enqueue(object: Callback<CommentResponse> {

            override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                if (response.body() != null) onCommentsCallback.onSuccess()
                else onCommentsCallback.onError()
            }

            override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                onCommentsCallback.onError()
            }

        })
    }

    interface OnCommentSentCallback: OnErrorCallback {
        fun onSuccess()
    }

    interface OnCommentsCallback: OnErrorCallback {
        fun onSuccess(comments: List<Comment>)
    }

    interface OnProductsCallback: OnErrorCallback {
        fun onSuccess(products: List<Product>?)
    }

    interface OnAuthCallback: OnErrorCallback {
        fun onAuth(success: Boolean)
    }

    interface OnErrorCallback {
        fun onError()
    }
}