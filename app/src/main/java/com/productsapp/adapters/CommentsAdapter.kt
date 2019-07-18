package com.productsapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.productsapp.R
import com.productsapp.api.model.Comment

class CommentsAdapter(private var listComments: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listComments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = listComments[position]
        holder.apply {
            textComment?.text = comment.text
            textCommentOwner?.text = comment.commentOwner.username
            textRate?.text = "${comment.rate}/5"
            textDate?.text = comment.date
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textCommentOwner: TextView? = null
        var textRate: TextView? = null
        var textComment: TextView? = null
        var textDate: TextView? = null

        init {
            textCommentOwner = itemView.findViewById(R.id.textCommentOwner)
            textRate = itemView.findViewById(R.id.textRate)
            textComment = itemView.findViewById(R.id.textComment)
            textDate = itemView.findViewById(R.id.textDate)
        }
    }
}