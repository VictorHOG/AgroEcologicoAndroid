package com.example.agroecologico

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import com.example.agroecologico.databinding.ActivityVendorAddProductBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VendorAddProduct : AppCompatActivity() {

    private lateinit var mBinding: ActivityVendorAddProductBinding
    private var mImageUri: Uri? = null
    private lateinit var layoutList:LinearLayout

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

        layoutList = mBinding.linearLayoutUnitList

        showImageOptions()
        saveInDatabase()
        addUnit()

    }

    private fun removeUnit(view:View) {
        layoutList.removeView(view)
    }

    private fun addUnit() {

        var unitList:MutableList<String> = ArrayList()

        unitList.add("Libra")
        unitList.add("Unidad")
        unitList.add("Atao")


        mBinding.buttonAddUnit.setOnClickListener {
            var unitView = getLayoutInflater().inflate(R.layout.row_add_unit, null, false)

            var editText:EditText = unitView.findViewById(R.id.editTextPriceUnit)
            var spinnerUnit:AppCompatSpinner = unitView.findViewById(R.id.spinnerUnit)
            var imageClose:ImageView = unitView.findViewById(R.id.imageViewRemove)

            var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unitList)
            spinnerUnit.setAdapter(arrayAdapter)

            imageClose.setOnClickListener {
                layoutList.removeView(unitView)
            }

            layoutList.addView(unitView)
        }
    }

    private fun saveInDatabase() {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        mStorageReference = FirebaseStorage.getInstance().getReference("$PATH_STORAGE$fileName")

        mBinding.buttonAddProduct.setOnClickListener {

            if (checkIfValidAndRead()) {

                if (mImageUri != null){

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

                } else {
                    Toast.makeText(this, "Debe añadir una imagen.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun checkIfValidAndRead(): Boolean {
        var result:Boolean = true

        var count = layoutList.getChildCount()

        Log.d("numero ", count.toString())

        if (count != 0){
            for(i in 0..(count-1)) {
                Log.d("numero dentro del for", i.toString())
                var itemUnitView: View = layoutList.getChildAt(i)

                var editText:EditText = itemUnitView.findViewById(R.id.editTextPriceUnit)
                var spinnerUnit:AppCompatSpinner = itemUnitView.findViewById(R.id.spinnerUnit)

                if (editText.getText().toString().equals("")) {
                    result = false
                    break
                }

                if (spinnerUnit.getSelectedItemPosition() == 0) {
                    result = false
                    break
                }

            }
        }


        if (mBinding.productName.text?.isNotEmpty() != true) {
            result = false
            Toast.makeText(this, "Debe añadir el nombre del producto.", Toast.LENGTH_SHORT).show()
        }else if (count == 0) {
            result = false
            Toast.makeText(this, "Debe añadir por lo menos una unidad de venta.", Toast.LENGTH_SHORT).show()
        }else if (!result) {
            Toast.makeText(this, "Debe completrar los campos.", Toast.LENGTH_SHORT).show()
        }

        return result
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