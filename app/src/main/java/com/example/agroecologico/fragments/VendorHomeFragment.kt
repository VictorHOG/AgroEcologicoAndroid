package com.example.agroecologico.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroecologico.R
import com.example.agroecologico.adapters.ImageStallAdapter
import com.example.agroecologico.adapters.ProductVendorAdapter
import com.example.agroecologico.adapters.WorkerAdapter
import com.example.agroecologico.data.ImageStall
import com.example.agroecologico.data.Product

import com.example.agroecologico.data.Worker

import com.example.agroecologico.databinding.FragmentVendorHomeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VendorHomeFragment : Fragment() {

    private lateinit var mBinding: FragmentVendorHomeBinding

    private lateinit var myContext: FragmentActivity

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val collectionReference: CollectionReference = database.collection("Stalls")
    private val userId = fAuth.getCurrentUser()?.getEmail()
    private val collectionReference: CollectionReference = database.collection("Stalls").document(userId.toString()).collection("Farmland")
    private val collectionReference2: CollectionReference = database.collection("Stalls").document(userId.toString()).collection("Workers")

    private val collectionReference3 = database.collection("Products").whereEqualTo("stall",userId.toString())

    private lateinit var imageStallAdapter: ImageStallAdapter
    private lateinit var workerAdapter: WorkerAdapter
    private lateinit var productAdapter: ProductVendorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentVendorHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViewMyStall()
        setUpRecyclerViewProducts()
        setUpRecyclerViewMyTeam()



        mBinding.buttonVendor.setOnClickListener {

            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(myContext.getSupportFragmentManager(), bottomSheetFragment.getTag())

        }

        mBinding.recyclerViewVendorMyStall.setVisibility(View.VISIBLE)

        mBinding.buttonVendorMyStall.setOnClickListener {
            mBinding.recyclerViewVendorMyStall.setVisibility(View.VISIBLE)
            mBinding.recyclerViewVendorMyTeam.setVisibility(View.GONE)
            mBinding.recyclerViewVendorProducts.setVisibility(View.GONE)
        }

        mBinding.buttonVendorMyTeam.setOnClickListener {
            mBinding.recyclerViewVendorMyStall.setVisibility(View.GONE)
            mBinding.recyclerViewVendorMyTeam.setVisibility(View.VISIBLE)
            mBinding.recyclerViewVendorProducts.setVisibility(View.GONE)
        }

        mBinding.buttonVendorProducts.setOnClickListener {
            mBinding.recyclerViewVendorMyStall.setVisibility(View.GONE)
            mBinding.recyclerViewVendorMyTeam.setVisibility(View.GONE)
            mBinding.recyclerViewVendorProducts.setVisibility(View.VISIBLE)
        }
    }

    private fun setUpRecyclerViewMyStall() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ImageStall> = FirestoreRecyclerOptions.Builder<ImageStall>()
            .setQuery(query, ImageStall::class.java)
            .build()

        imageStallAdapter = ImageStallAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewVendorMyStall.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerViewVendorMyStall.adapter = imageStallAdapter
    }

    private fun setUpRecyclerViewProducts() {
        val query: Query = collectionReference3
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Product> = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()

        productAdapter = ProductVendorAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewVendorProducts.setHasFixedSize(true)
        mBinding.recyclerViewVendorProducts.layoutManager = GridLayoutManager(requireContext(), 3)
        mBinding.recyclerViewVendorProducts.adapter = productAdapter
    }

    private fun setUpRecyclerViewMyTeam() {
        val query: Query = collectionReference2
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Worker> = FirestoreRecyclerOptions.Builder<Worker>()
            .setQuery(query, Worker::class.java)
            .build()

        workerAdapter = WorkerAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewVendorMyTeam.setHasFixedSize(true)
        mBinding.recyclerViewVendorMyTeam.layoutManager = GridLayoutManager(requireContext(), 2)
        mBinding.recyclerViewVendorMyTeam.adapter = workerAdapter
    }

    override fun onStart() {
        super.onStart()
        imageStallAdapter.startListening()
        workerAdapter.startListening()
        productAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageStallAdapter.stopListening()
        workerAdapter.stopListening()
        productAdapter.stopListening()
    }

}