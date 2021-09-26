package com.example.agroecologico

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.agroecologico.databinding.ActivityVendorAddWorkerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class VendorAddWorker : AppCompatActivity() {

    private lateinit var mBinding: ActivityVendorAddWorkerBinding
    private var mImageUri: Uri? = null

    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail().toString()
    private lateinit var mStorageReference : StorageReference
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val REQUEST_IMAGE_GALLERY: Int = 100
    private val REQUEST_IMAGE_CAMERA: Int = 101
    private val PATH_STORAGE = "workers/"+userId+"/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVendorAddWorkerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        showImageOptions()
        saveInDatabase()
    }

    private fun saveInDatabase() {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        mStorageReference = FirebaseStorage.getInstance().getReference("$PATH_STORAGE$fileName")

        mBinding.buttonAddWorker.setOnClickListener {

            if (mImageUri != null && mBinding.WorkerName.text?.isNotEmpty() == true) {
                mStorageReference.putFile(mImageUri!!)
                    .addOnCompleteListener {}
                    .addOnSuccessListener {
                        mStorageReference.getDownloadUrl().addOnSuccessListener {
                            database.collection("Stalls").document(userId).collection("Workers").add(
                                hashMapOf(
                                    "name" to mBinding.WorkerName.text.toString(),
                                    "urlImage" to it.toString()
                                )
                            )
                        }
                        Toast.makeText(this, "El trabajador ha sido añadido", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {}
                    .addOnFailureListener {}
            }
        }
    }

    private fun showImageOptions() {

        val itemsDialog = arrayOf("Elegir foto de la galeria", "Tomar foto nueva")

        mBinding.imageButtonAddImageWorker.setOnClickListener {
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
                mBinding.imageViewAddImageWorker.setImageURI(mImageUri)
            } else if (requestCode == REQUEST_IMAGE_CAMERA) {
                val image:Bundle? = data?.extras
                val imageWorker:Bitmap? = image?.getParcelable("data")
                mBinding.imageViewAddImageWorker.setImageBitmap(imageWorker)
            }
        }
    }

}