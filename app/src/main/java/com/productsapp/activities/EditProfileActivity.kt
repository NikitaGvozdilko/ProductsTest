package com.productsapp.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.productsapp.ProductsApp
import com.productsapp.R
import com.productsapp.database.model.ProfileModel
import kotlinx.android.synthetic.main.activity_edit_profile.*


const val RQ_PICK_FROM_GALLARY: Int = 1
class EditProfileActivity : AppCompatActivity() {
    private var imageSrc: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        checkPermissions()

        initViews()
    }

    private fun initViews() {
        val profile = ProductsApp.appDatabase?.getProfile()
        profile?.let {
            editName.setText(it.name)
            editSurname.setText(it.surname)
            if (it.image != null && it.image?.isNotEmpty()!!)
                Glide.with(this@EditProfileActivity).load(it.image).apply(RequestOptions().circleCrop()).into(imageAvatar)
            else
                Glide.with(this@EditProfileActivity).load(R.drawable.ic_no_image).apply(RequestOptions().circleCrop()).into(imageAvatar)

        } ?: Glide.with(this@EditProfileActivity).load(R.drawable.ic_no_image).apply(RequestOptions().circleCrop()).into(imageAvatar)


        imageAvatar.setOnClickListener {
            if (checkPermissions()) {
                val galleryIntent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent, RQ_PICK_FROM_GALLARY)
            }
        }

        buttonSave.setOnClickListener {
            ProductsApp.appDatabase?.saveProfile(
                    ProfileModel(editName.text.toString(), editSurname.text.toString(), imageSrc))
            startMainActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RQ_PICK_FROM_GALLARY -> {
                if (resultCode == Activity.RESULT_OK) {
                    imageSrc = data?.data.toString()
                    Glide.with(this@EditProfileActivity).load(imageSrc).apply(RequestOptions().circleCrop()).into(imageAvatar)
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        var allGranted = true
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1)
            allGranted = false
        } else if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                2)
            allGranted = false
        }
        return allGranted
    }

    private fun startMainActivity() {
        startActivity(Intent(this@EditProfileActivity, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
