package com.example.agroecologico.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroecologico.R
import com.example.agroecologico.adapters.ImageStallAdapter
import com.example.agroecologico.data.ImageStall
import com.example.agroecologico.data.Stall
import com.example.agroecologico.databinding.FragmentVendorHomeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class VendorHomeFragment : Fragment() {

    private lateinit var mBinding: FragmentVendorHomeBinding

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val collectionReference: CollectionReference = database.collection("Stalls")
    private val userId = fAuth.getCurrentUser()?.getEmail()
    private val collectionReference: CollectionReference = database.collection("Stalls").document(userId.toString()).collection("Farmland")

    private lateinit var imageStallAdapter: ImageStallAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentVendorHomeBinding.inflate(inflater, container, false)
        return mBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<ImageStall> = FirestoreRecyclerOptions.Builder<ImageStall>()
            .setQuery(query, ImageStall::class.java)
            .build()

        imageStallAdapter = ImageStallAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewVendorMyStall.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerViewVendorMyStall.adapter = imageStallAdapter
    }

    override fun onStart() {
        super.onStart()
        imageStallAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageStallAdapter.stopListening()
    }

}