package com.productsapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.productsapp.R
import com.productsapp.api.model.Product

class ProductsAdapter (private var listProducts: List<Product>): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle?.text = listProducts[position].title
        holder.textSubscription?.text = listProducts[position].text
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textTitle: TextView? = null
        var textSubscription: TextView? = null
        init {
            textTitle = itemView.findViewById(R.id.textTitle)
            textSubscription = itemView.findViewById(R.id.textSubscribtion)
        }
    }
}
