package com.example.agroecologico.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agroecologico.R

/**
 * A simple [Fragment] subclass.
 * Use the [AdminStallsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminStallsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_stalls, container, false)
    }
}