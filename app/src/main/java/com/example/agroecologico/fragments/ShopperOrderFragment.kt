package com.example.agroecologico.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agroecologico.R
import com.example.agroecologico.databinding.FragmentShopperOrderBinding

class ShopperOrderFragment : Fragment() {

    private lateinit var mBinding: FragmentShopperOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentShopperOrderBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}