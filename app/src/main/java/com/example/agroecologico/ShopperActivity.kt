package com.example.agroecologico

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.agroecologico.databinding.ActivityShopperBinding
import com.example.agroecologico.fragments.*
import kotlin.concurrent.fixedRateTimer

class ShopperActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityShopperBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityShopperBinding.inflate(layoutInflater)
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

        val homeFragment = ShopperHomeFragment()
        val offersFragment = ShopperOffersFragment()
        val buyFragment = ShopperBuyFragment()
        val orderFragment = ShopperOrderFragment()
        val profileFragment = ProfileFragment()

        activeFragment = homeFragment

        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerShopper, homeFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerShopper, offersFragment)
            .hide(offersFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerShopper, buyFragment)
            .hide(buyFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerShopper, orderFragment)
            .hide(orderFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerShopper, profileFragment)
            .hide(profileFragment)
            .commit()

        mBinding.bottomNavShopper.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.item_home_shopper -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(homeFragment)
                        .commit()
                    activeFragment = homeFragment
                    true
                }
                R.id.item_offers_shopper-> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(offersFragment)
                        .commit()
                    activeFragment = offersFragment
                    true
                }
                R.id.item_buy_shopper -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(buyFragment)
                        .commit()
                    activeFragment = buyFragment
                    true
                }
                R.id.item_order_shopper -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(orderFragment)
                        .commit()
                    activeFragment = orderFragment
                    true
                }
                R.id.item_profile_shopper -> {
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