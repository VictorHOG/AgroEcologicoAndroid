package com.example.agroecologico.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
            dismiss()

            val builder = AlertDialog.Builder(requireContext())
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.custom_dialog,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editTextName)

            with(builder) {
                setTitle("Nombre Del Puesto De Venta")
                setPositiveButton("Aceptar"){dialog, which ->
                    Toast.makeText(context, "Boton Aceptar", Toast.LENGTH_SHORT).show()
                    val name = editText.text.toString()
                    Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("Cancelar"){dialog, which ->
                    Toast.makeText(context, "Boton Cancelar", Toast.LENGTH_SHORT).show()
                }
                setView(dialogLayout)
                show()
            }
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