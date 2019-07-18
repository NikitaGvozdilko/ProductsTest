package com.productsapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.productsapp.NetworkManager
import com.productsapp.ProductsApp
import com.productsapp.R
import com.productsapp.adapters.ProductsAdapter
import com.productsapp.api.model.Product
import com.productsapp.utils.AppPref
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var productsAdapter: ProductsAdapter? = null
    private val onProductsCallback = object: NetworkManager.OnProductsCallback {
        override fun onSuccess(products: List<Product>?) {
            if(products != null) {
                productsAdapter = ProductsAdapter(products)
                recyclerProducts.adapter = productsAdapter
            }
        }

        override fun onError() {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecycler()
        ProductsApp.networkManager.getProducts(AppPref.getToken(this), onProductsCallback)
    }

    private fun initRecycler() {
        recyclerProducts.layoutManager = LinearLayoutManager(this)
    }
}
