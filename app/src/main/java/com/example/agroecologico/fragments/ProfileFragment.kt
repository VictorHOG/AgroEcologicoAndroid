package com.example.agroecologico.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.agroecologico.AdminActivity
import com.example.agroecologico.AuthActivity
import com.example.agroecologico.R
import com.example.agroecologico.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popupMenu()
        userInfo()

    }

    private fun userInfo() {
        val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()

        val userId = fAuth.getCurrentUser()?.getEmail()

        val documentReference: DocumentReference = database.collection("Users").document(userId.toString())

        documentReference.get().addOnSuccessListener {
            if(it.exists()) {
                val name = it.getString("name")
                val email = it.getString("email")
                val phoneNumber = it.getString("phoneNumber")
                val image = it.getString("urlImage")

                mBinding.textViewNameUserProfile.setText(name)
                mBinding.textViewEmailProfile.setText(email)
                mBinding.textViewPhoneNumberProfile.setText(phoneNumber)

                Glide.with(requireContext())
                    .load(image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(mBinding.imageViewProfile)

            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

    }

    private fun popupMenu() {
        mBinding.settingsProfile.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_popup_menu_about -> {
                        Toast.makeText(requireContext(), "Acerca de la aplicacion", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.item_popup_menu_logout -> {
                        Toast.makeText(requireContext(), "Cerrar sesión", Toast.LENGTH_SHORT).show()

                        val prefs = this.getActivity()
                            ?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                            ?.edit()
                        prefs?.clear()
                        prefs?.apply()

                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(getActivity(), AuthActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.popup_menu_profile)
            popupMenu.show()
        }
    }


}