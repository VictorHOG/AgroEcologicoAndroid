package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.agroecologico.databinding.ActivityShopperOrderBinding
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ShopperOrderActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityShopperOrderBinding

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail()
    private val collectionReference = database.collection("AddToCart").whereEqualTo("shopperUser",userId.toString())
    private val collectionReference2 = database.collection("OrderList")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityShopperOrderBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        var deliveryType = ""

        mBinding.buttonConfirmOrder.setOnClickListener {
            mBinding.chipGroupDeliveryType.checkedChipIds.forEach{
                val chip = findViewById<Chip>(it)
                deliveryType = chip.text.toString()
            }
            Toast.makeText(this, deliveryType, Toast.LENGTH_SHORT).show()

        }
    }
}