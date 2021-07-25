package com.example.agroecologico.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agroecologico.adapters.UnitAdapter
import com.example.agroecologico.data.UnitModel
import com.example.agroecologico.databinding.FragmentAdminUnitsBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AdminUnitsFragment : Fragment() {

    private lateinit var mBinding: FragmentAdminUnitsBinding

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = database.collection("SalesUnits")

    private lateinit var unitAdapter: UnitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentAdminUnitsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        mBinding.buttonAdminUnit.setOnClickListener { createSalesUnit() }
    }

    private fun setUpRecyclerView() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<UnitModel> = FirestoreRecyclerOptions.Builder<UnitModel>()
            .setQuery(query, UnitModel::class.java)
            .build()

        unitAdapter = UnitAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewAdminUnits.layoutManager = LinearLayoutManager(requireContext()) //No tengo claro como pasar el contexto
        mBinding.recyclerViewAdminUnits.adapter = unitAdapter
    }

    override fun onStart() {
        super.onStart()
        unitAdapter.startListening()
    }


    override fun onDestroy() {
        super.onDestroy()
        unitAdapter.stopListening()
    }

    private fun createSalesUnit() {
        val nameUnit = mBinding.unitName
        if (nameUnit.text?.isNotEmpty() == true) {
            FirebaseFirestore.getInstance().collection("SalesUnits")
                .document(nameUnit.text.toString()).set(
                    hashMapOf(
                        "name" to nameUnit.text.toString()
                    )
                )
        }
    }


}