package com.example.agroecologico.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroecologico.R
import com.example.agroecologico.ShopperOrderActivity
import com.example.agroecologico.adapters.ShoppingCartAdapter
import com.example.agroecologico.data.ShoppingCart
import com.example.agroecologico.databinding.FragmentShopperBuyBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ShopperBuyFragment : Fragment() {

    private lateinit var mBinding: FragmentShopperBuyBinding

    private lateinit var shoppingCartAdapter: ShoppingCartAdapter

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userId = fAuth.getCurrentUser()?.getEmail()
    private val collectionReference = database.collection("AddToCart").whereEqualTo("shopperUser",userId.toString())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentShopperBuyBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        LocalBroadcastManager.getInstance(requireActivity())
            .registerReceiver(broadCastReceiver, IntentFilter("MyTotalAmount"))

        mBinding.buyNow.setOnClickListener {
            val intent = Intent(requireContext(),ShopperOrderActivity::class.java)
            startActivity(intent)
        }

    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var totalBill = intent!!.getIntExtra("totalAmount",0)
            mBinding.textViewTotalPriceCart.text = "Total : $ " + totalBill.toString()
        }

    }

    private fun setUpRecyclerView() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ShoppingCart> = FirestoreRecyclerOptions.Builder<ShoppingCart>()
            .setQuery(query, ShoppingCart::class.java)
            .build()

        shoppingCartAdapter = ShoppingCartAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerViewCart.adapter = shoppingCartAdapter
    }

    override fun onStart() {
        super.onStart()
        shoppingCartAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        shoppingCartAdapter.stopListening()
    }

}