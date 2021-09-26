package com.example.agroecologico

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.agroecologico.databinding.ActivityVendorAddStallImageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class VendorAddStallImage : AppCompatActivity() {
    private lateinit var mBinding:ActivityVendorAddStallImageBinding
    private var mImageUri:Uri? = null

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail().toString()
    private lateinit var mStorageReference : StorageReference
    //private val storageReference: StorageReference = FirebaseStorage.getInstance().getReference()
    //private val imageReference = storageReference.child("farmland/" + userId)

    private val REQUEST_IMAGE_GALLERY: Int = 100
    private val REQUEST_IMAGE_CAMERA: Int = 101
    private val PATH_IMAGENES = "Imagenes"
    private val PATH_STORAGE = "farmland/"+userId+"/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVendorAddStallImageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val itemsDialog = arrayOf("Elegir foto de la galeria", "Tomar foto nueva")

        mBinding.imageButtonAddImage.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setItems(itemsDialog) {_, which->
                    when(which) {
                        0 -> {
                            Snackbar.make(it, "Galeria", Snackbar.LENGTH_SHORT).show()
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
                        }
                        1 -> {
                            Snackbar.make(it, "Tomar foto", Snackbar.LENGTH_SHORT).show()
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
                           /* if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
                            }*/
                        }
                    }

                }.show()
        }



        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        mStorageReference = FirebaseStorage.getInstance().getReference("$PATH_STORAGE$fileName")

        mBinding.buttonAddStallImage.setOnClickListener {
            //val storageReference = mStorageReference.child(PATH_IMAGENES).child("la_Imagen")
            if (mImageUri != null){
                mStorageReference.putFile(mImageUri!!)
                    .addOnCompleteListener {
                        Toast.makeText(this, "se cargo la imagen", Toast.LENGTH_SHORT).show()
                    }
                    .addOnSuccessListener {

                        mStorageReference.getDownloadUrl().addOnSuccessListener {
                            database.collection("Stalls").document(userId).collection("Farmland").add(
                                hashMapOf("urlImage" to it.toString())
                            )
                        }



                        Toast.makeText(this, "se Subio la imagen", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Errores", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "No funciono", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                mImageUri = data?.data
                mBinding.imageViewAddImage.setImageURI(mImageUri)

            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                mImageUri = data?.data
                val image:Bundle? = data?.extras
                val imageStall:Bitmap? = image?.getParcelable("data")
                mBinding.imageViewAddImage.setImageBitmap(imageStall)
            }
        }

    }
}