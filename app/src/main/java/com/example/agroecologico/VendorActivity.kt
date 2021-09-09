package com.example.agroecologico

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.agroecologico.databinding.ActivityVendorBinding
import com.example.agroecologico.fragments.ProfileFragment
import com.example.agroecologico.fragments.VendorHomeFragment
import com.example.agroecologico.fragments.VendorOffersFragment
import com.example.agroecologico.fragments.VendorOrderFragment


class VendorActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityVendorBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var activeFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVendorBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpBottomNav()

        val bundle = intent.extras
        val email = bundle?.getString("email")

        //Guardar datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.apply()
    }

    private fun setUpBottomNav() {
        fragmentManager = supportFragmentManager

        val homeFragment = VendorHomeFragment()
        val offersFragment = VendorOffersFragment()
        val orderFragment = VendorOrderFragment()
        val profileFragment = ProfileFragment()

        activeFragment = homeFragment

        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerVendor, homeFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerVendor, offersFragment)
            .hide(offersFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerVendor, orderFragment)
            .hide(orderFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerVendor, profileFragment)
            .hide(profileFragment)
            .commit()

        mBinding.bottomNavVendor.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.item_home_vendor ->{
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.item_offers_vendor ->{
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(offersFragment)
                        .commit()
                    activeFragment = offersFragment
                    true
                }
                R.id.item_order ->{
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(orderFragment)
                        .commit()
                    activeFragment = orderFragment
                    true
                }
                R.id.item_profile_vendor ->{
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(profileFragment)
                        .commit()
                    activeFragment = profileFragment
                    true
                }
                else -> false
            }
        }
    }
}