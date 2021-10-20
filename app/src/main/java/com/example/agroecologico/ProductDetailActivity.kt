package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.data.Product
import com.example.agroecologico.data.ShoppingCart
import com.example.agroecologico.databinding.ActivityProductDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProductDetailBinding
    private lateinit var productDetails: Product
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail().toString()

    private var ShoppingCartList: MutableList<ShoppingCart> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        var buttonUnitActive = false
        var totalQuantity = 1
        var totalPrice = 0
        var nameUnit = ""
        var priceUnit = "1"
        var quantity = mBinding.textViewQuantity


        productDetails = Product()
        if (intent.hasExtra("detail")) {
            productDetails = intent.getParcelableExtra("detail")!!

            Glide.with(this)
                .load(productDetails.urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imageViewDetailedProduct)

            mBinding.textViewDetailedProductName.text = productDetails.name

            val mapPrices = productDetails.price
            val layoutList = mBinding.linearLayoutUnits

            for (key in mapPrices.keys) {
                println("key " + key)
                println(mapPrices[key])
                println("prueba " + mapPrices.values)
                println("prueba " + mapPrices.keys)

                val button = Button(this)
                button.text = key
                button.setOnClickListener {
                    buttonUnitActive = true
                    mBinding.textViewDetailedPrice.text =
                        mapPrices[key] + "\n" + "Precio por " + key
                    nameUnit = key
                    priceUnit = mapPrices[key].toString()
                    totalPrice = priceUnit.toInt() * totalQuantity
                    mBinding.textViewTotalPrice.text = totalPrice.toString()

                }

                layoutList.addView(button)
            }

            // if (buttonUnitActive) {
            mBinding.imageButtonAddItem.setOnClickListener {
                totalQuantity++
                quantity.setText("Cantidad: " + totalQuantity.toString() + " " + nameUnit)
                totalPrice = priceUnit.toInt() * totalQuantity
                mBinding.textViewTotalPrice.text = totalPrice.toString()
            }

            mBinding.imageButtonRemoveItem.setOnClickListener {
                if (totalQuantity > 1) {
                    totalQuantity--
                    quantity.setText("Cantidad: " + totalQuantity.toString() + " " + nameUnit)
                    totalPrice = priceUnit.toInt() * totalQuantity
                    mBinding.textViewTotalPrice.text = totalPrice.toString()
                }
            }


            //}

            mBinding.buttonAddToCart.setOnClickListener {
                val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                val now = Date()
                val dateAddToCart = formatter.format(now)

                val cartMap = hashMapOf(
                    "shopperUser" to userId,
                    "productName" to productDetails.name,
                    "productImage" to productDetails.urlImage,
                    "productPrice" to priceUnit,
                    "productUnit" to nameUnit,
                    "totalQuantity" to totalQuantity.toString(),
                    "totalPrice" to totalPrice.toString(),
                    "currentDate" to dateAddToCart,
                )


                database.collection("AddToCart").add(
                    cartMap
                ).addOnCompleteListener {

                    if (it.isSuccessful){


                       /* val shoppingCart:ShoppingCart = it to ShoppingCart
                        ShoppingCartList.add()*/
                    }

                    Toast.makeText(this, "Añadido al carrito de compras", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }


        }

    }
}