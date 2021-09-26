package com.example.agroecologico.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.agroecologico.R
import com.example.agroecologico.VendorAddProduct
import com.example.agroecologico.VendorAddStallImage
import com.example.agroecologico.VendorAddWorker
import com.example.agroecologico.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mBinding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.imageViewCancelBtn.setOnClickListener {
            dismiss()
        }

        mBinding.linearLayoutChangeName.setOnClickListener {
            Toast.makeText(context, "mensaje de prueba ChangeName", Toast.LENGTH_SHORT).show()
        }

        mBinding.linearLayoutAddStallImage.setOnClickListener {
            Toast.makeText(context, "mensaje de prueba AddStallImage", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), VendorAddStallImage::class.java)
            startActivity(intent)
            dismiss()
        }

        mBinding.linearLayoutAddProduct.setOnClickListener {
            Toast.makeText(context, "mensaje de prueba AddProduct", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), VendorAddProduct::class.java)
            startActivity(intent)
            dismiss()
        }

        mBinding.linearLayoutAddWorker.setOnClickListener {
            Toast.makeText(context, "mensaje de prueba AddWorker", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), VendorAddWorker::class.java)
            startActivity(intent)
            dismiss()
        }
    }

}