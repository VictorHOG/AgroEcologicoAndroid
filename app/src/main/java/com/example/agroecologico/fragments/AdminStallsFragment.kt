package com.example.agroecologico.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroecologico.R
import com.example.agroecologico.adapters.StallAdapter
import com.example.agroecologico.data.Stall
import com.example.agroecologico.databinding.FragmentAdminStallsBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminStallsFragment : Fragment() {

    private lateinit var mBinding: FragmentAdminStallsBinding

    private val database:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = database.collection("Stalls")

    private lateinit var stallAdapter: StallAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminStallsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Stall> = FirestoreRecyclerOptions.Builder<Stall>()
            .setQuery(query, Stall::class.java)
            .build()

        stallAdapter = StallAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewAdminStalls.layoutManager = LinearLayoutManager(requireContext()) //No tengo claro como pasar el contexto
        mBinding.recyclerViewAdminStalls.adapter = stallAdapter
    }

    override fun onStart() {
        super.onStart()
        stallAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        stallAdapter!!.stopListening()
    }
}