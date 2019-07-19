package com.productsapp.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.productsapp.R
import com.productsapp.api.model.Comment
import com.productsapp.model.ProductModel

class ProductsAdapter (private val context: Context,
                       private var listProducts: List<ProductModel>,
                       private val onAddCommentClickListener: OnAddCommentClickListener): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProducts[position]
        holder.apply {
            textTitle?.text = product.title
            textSubscription?.text = product.text
            if (!product.image.isNullOrEmpty())
                Glide.with(context).load("http://smktesting.herokuapp.com/static/${product.image}").into(imagePhoto!!)
            else
                Glide.with(context).load(R.drawable.ic_no_image).into(imagePhoto!!)

            handleRecycler(holder, product)

            holder.layoutAddComment?.setOnClickListener {
                onAddCommentClickListener.onClicked(product.id)
            }
        }
    }

    private fun handleRecycler(holder: ViewHolder, product: ProductModel) {
        holder.apply {
            if (!product.commentsList.isNullOrEmpty()) {
                if (product.commentsList.size > 1) titleComment?.text = "Comments (${product.commentsList.size})"
                recyclerComments?.layoutManager = LinearLayoutManager(context)
                recyclerComments?.adapter = CommentsAdapter(product.commentsList)
                if (product.showComments) recyclerComments?.visibility = View.VISIBLE
                else recyclerComments?.visibility = View.GONE
                imageShowHide?.visibility = View.VISIBLE
                imageShowHide?.setOnClickListener {
                    if (recyclerComments?.visibility == View.VISIBLE) {
                        product.showComments = false
                        recyclerComments?.visibility = View.GONE
                    } else {
                        product.showComments = true
                        recyclerComments?.visibility = View.VISIBLE
                    }
                    imageShowHide?.animate()?.cancel()
                    imageShowHide?.animate()?.rotationBy(180.0f)
                }
            } else imageShowHide?.visibility = View.GONE
        }
    }

    fun updateComments(comments: List<Comment>) {
        val first = listProducts.first { it.id == comments[0].product }
        first.commentsList = comments
        notifyItemChanged(listProducts.indexOf(first))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textTitle: TextView? = null
        var titleComment: TextView? = null
        var textSubscription: TextView? = null
        var imagePhoto: ImageView? = null
        var imageShowHide: ImageView? = null
        var recyclerComments: RecyclerView? = null
        var layoutAddComment: LinearLayout? = null
        init {
            textTitle = itemView.findViewById(R.id.textTitle)
            titleComment = itemView.findViewById(R.id.textTitleComments)
            textSubscription = itemView.findViewById(R.id.textSubscribtion)
            imagePhoto = itemView.findViewById(R.id.imagePhoto)
            imageShowHide = itemView.findViewById(R.id.imageShowHide)
            recyclerComments = itemView.findViewById(R.id.recyclerComments)
            layoutAddComment = itemView.findViewById(R.id.layoutAddNewComment)
        }
    }

    interface OnAddCommentClickListener {
        fun onClicked(productId: String)
    }
}
