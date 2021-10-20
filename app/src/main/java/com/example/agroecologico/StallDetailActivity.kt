package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.agroecologico.adapters.ProductShopperAdapter
import com.example.agroecologico.adapters.ProductVendorAdapter
import com.example.agroecologico.data.Product
import com.example.agroecologico.databinding.ActivityStallDetailBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.synnapps.carouselview.CarouselView

class StallDetailActivity : AppCompatActivity() {
    private lateinit var mBinding:ActivityStallDetailBinding

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var productAdapter: ProductShopperAdapter

    var imageArray:ArrayList<Int> = ArrayList()
    var carouselView: CarouselView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStallDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val bundle = intent.extras
        val nameStall = bundle?.getString("name")
        val email = bundle?.getString("email")

        mBinding.nameStall.setText(nameStall)


        setUpCarouselView()
        setUpRecyclerView(email ?: "")
    }

    private fun setUpCarouselView() {
        imageArray.add(R.drawable.one)
        imageArray.add(R.drawable.two)
        imageArray.add(R.drawable.three)
        imageArray.add(R.drawable.four)

        carouselView = mBinding.carouselViewStall

        carouselView!!.pageCount = imageArray.size

        carouselView!!.setImageListener { position, imageView ->
            imageView.setImageResource(imageArray[position])
        }
    }

    private fun setUpRecyclerView(email: String) {
        val collectionReference = database.collection("Products").whereEqualTo("stall",email)

        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Product> = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()

        productAdapter = ProductShopperAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewShopperProducts.setHasFixedSize(true)
        mBinding.recyclerViewShopperProducts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mBinding.recyclerViewShopperProducts.adapter = productAdapter

    }

    override fun onStart() {
        super.onStart()
        productAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        productAdapter.stopListening()
    }


}