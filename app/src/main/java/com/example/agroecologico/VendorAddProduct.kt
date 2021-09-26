package com.example.agroecologico

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.agroecologico.databinding.ActivityVendorAddProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class VendorAddProduct : AppCompatActivity() {

    private lateinit var mBinding: ActivityVendorAddProductBinding
    private var mImageUri: Uri? = null

    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail().toString()
    private lateinit var mStorageReference : StorageReference
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val REQUEST_IMAGE_GALLERY: Int = 100
    private val REQUEST_IMAGE_CAMERA: Int = 101
    private val PATH_STORAGE = "products/"+userId+"/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVendorAddProductBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        showImageOptions()
        saveInDatabase()
    }

    private fun saveInDatabase() {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        mStorageReference = FirebaseStorage.getInstance().getReference("$PATH_STORAGE$fileName")

        mBinding.buttonAddProduct.setOnClickListener {

            if (mImageUri != null && mBinding.productName.text?.isNotEmpty() == true) {
                mStorageReference.putFile(mImageUri!!)
                    .addOnCompleteListener {}
                    .addOnSuccessListener {
                        mStorageReference.getDownloadUrl().addOnSuccessListener {
                            database.collection("Products").add(
                                hashMapOf(
                                    "name" to mBinding.productName.text.toString(),
                                    "stall" to userId,
                                    "urlImage" to it.toString()
                                )
                            )
                        }
                        Toast.makeText(this, "El Producto ha sido añadido", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {}
                    .addOnFailureListener {}
            }
        }
    }

    private fun showImageOptions() {

        val itemsDialog = arrayOf("Elegir foto de la galeria", "Tomar foto nueva")

        mBinding.imageButtonAddImageProduct.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setItems(itemsDialog) {_, which->
                    when(which) {
                        0 -> {
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
                        }
                        1 -> {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
                        }
                    }
                }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                mImageUri = data?.data
                mBinding.imageViewAddImageProduct.setImageURI(mImageUri)
            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                val image:Bundle? = data?.extras
                val imageProduct: Bitmap? = image?.getParcelable("data")
                mBinding.imageViewAddImageProduct.setImageBitmap(imageProduct)
            }
        }
    }

}