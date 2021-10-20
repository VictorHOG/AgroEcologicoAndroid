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
import com.example.agroecologico.databinding.FragmentShopperHomeBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.synnapps.carouselview.CarouselView

class ShopperHomeFragment : Fragment() {

    private lateinit var mBinding:FragmentShopperHomeBinding
    var imageArray:ArrayList<Int> = ArrayList()
    var carouselView: CarouselView? = null
    private lateinit var stallAdapter: StallAdapter

    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = database.collection("Stalls")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentShopperHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCarouselView()
        setUpRecyclerView()

    }

    private fun setUpCarouselView() {
        imageArray.add(R.drawable.one)
        imageArray.add(R.drawable.two)
        imageArray.add(R.drawable.three)
        imageArray.add(R.drawable.four)

        carouselView = mBinding.carouselView

        carouselView!!.pageCount = imageArray.size

        carouselView!!.setImageListener { position, imageView ->
            imageView.setImageResource(imageArray[position])
        }
    }

    private fun setUpRecyclerView() {
        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<Stall> = FirestoreRecyclerOptions.Builder<Stall>()
            .setQuery(query, Stall::class.java)
            .build()

        stallAdapter = StallAdapter(firestoreRecyclerOptions)

        mBinding.recyclerViewShopperStalls.layoutManager = LinearLayoutManager(requireContext()) //No tengo claro como pasar el contexto
        mBinding.recyclerViewShopperStalls.adapter = stallAdapter
    }

    override fun onStart() {
        super.onStart()
        stallAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        stallAdapter.stopListening()
    }

}