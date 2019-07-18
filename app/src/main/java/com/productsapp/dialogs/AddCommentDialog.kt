package com.productsapp.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.productsapp.R
import com.willy.ratingbar.BaseRatingBar
import com.willy.ratingbar.ScaleRatingBar


class AddCommentDialog(context: Activity, private val onDialogClickListener: OnDialogClickListener) {
    private var dialog: AlertDialog? = null
    private var ratingValue: Float? = null

    init {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Add comment")
        val view = context.layoutInflater
                .inflate(R.layout.dialog_add_comment, null) as ConstraintLayout
        val ratingBar: ScaleRatingBar = view.findViewById(R.id.layoutRatingBar)
        val editComment: EditText = view.findViewById(R.id.editComment)
        ratingBar.setOnRatingChangeListener(object : BaseRatingBar.OnRatingChangeListener {
            override fun onRatingChange(ratingBar: BaseRatingBar?, rating: Float, fromUser: Boolean) {
                ratingValue = rating
            }
        })
        dialogBuilder.setView(view)
        dialogBuilder.setPositiveButton("Add", object: DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
                if (editComment.text.isEmpty()) {
                    Toast.makeText(context, "Enter comment", Toast.LENGTH_LONG).show()
                    return
                }
                else if(ratingValue == null) {
                    Toast.makeText(context, "Select rating", Toast.LENGTH_LONG).show()
                    return
                }
                onDialogClickListener.onPositive(editComment.text.toString(), ratingValue!!)
            }

        })
        dialogBuilder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener{
            override fun onClick(dialogInterface: DialogInterface?, p1: Int) {
            }

        })
        dialog = dialogBuilder.create()
    }

    fun show() {
        dialog?.show()
    }

    interface OnDialogClickListener {
        fun onPositive(text: String, ratingValue: Float)
    }
}