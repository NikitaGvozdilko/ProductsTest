package com.productsapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.productsapp.NetworkManager
import com.productsapp.ProductsApp
import com.productsapp.R
import com.productsapp.adapters.ProductsAdapter
import com.productsapp.api.model.Comment
import com.productsapp.api.model.CommentToSend
import com.productsapp.api.model.Product
import com.productsapp.dialogs.AddCommentDialog
import com.productsapp.model.ProductModel
import com.productsapp.utils.AppPref
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var productsAdapter: ProductsAdapter? = null
    private var productId: String? = null

    private val onAddCommentClickListener = object : ProductsAdapter.OnAddCommentClickListener{
        override fun onClicked(productId: String) {
            this@MainActivity.productId = productId
            val dialog = AddCommentDialog(this@MainActivity, object: AddCommentDialog.OnDialogClickListener {
                override fun onPositive(text: String, ratingValue: Float) {
                    ProductsApp.networkManager.sendComment(AppPref.getToken(this@MainActivity),
                            productId, CommentToSend(text, ratingValue), onCommentSentCallback)
                }

            })
            dialog.show()
        }
    }

    private val onCommentSentCallback = object : NetworkManager.OnCommentSentCallback {
        override fun onSuccess() {
            if(productId != null)
                ProductsApp.networkManager.getComments(AppPref.getToken(this@MainActivity), listOf(productId!!), onGetCommentsCallback)
        }

        override fun onError() {
            productId = null
            Toast.makeText(this@MainActivity, "Comment wasn't send", Toast.LENGTH_LONG).show()
        }

    }

    private val onProductsCallback = object: NetworkManager.OnProductsCallback {
        override fun onSuccess(products: List<Product>?) {
            if(products != null) {
                ProductsApp.networkManager.getComments(AppPref.getToken(this@MainActivity), products.map { it.id }, onGetCommentsCallback)
                val convertList = ProductModel.convertList(products)
                productsAdapter = ProductsAdapter(this@MainActivity, convertList, onAddCommentClickListener)
                recyclerProducts.adapter = productsAdapter
            }
        }

        override fun onError() {

        }
    }

    private val onGetCommentsCallback = object: NetworkManager.OnCommentsCallback {
        override fun onSuccess(comments: List<Comment>) {
            if (!comments.isNullOrEmpty())
                productsAdapter?.updateComments(comments)
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
