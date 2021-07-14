package com.example.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.agroecologico.databinding.ActivityAdminBinding
import com.example.agroecologico.fragments.AdminNotificationsFragment
import com.example.agroecologico.fragments.AdminStallsFragment
import com.example.agroecologico.fragments.AdminUnitsFragment
import com.example.agroecologico.fragments.ProfileFragment

class AdminActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityAdminBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        fragmentManager = supportFragmentManager

        val stallsFragment = AdminStallsFragment()
        val unitsFragment = AdminUnitsFragment()
        val notificationsFragment = AdminNotificationsFragment()
        val profileFragment = ProfileFragment()

        activeFragment = stallsFragment

        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerAdmin, stallsFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerAdmin, unitsFragment)
            .hide(unitsFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerAdmin, notificationsFragment)
            .hide(notificationsFragment)
            .commit()
        fragmentManager.beginTransaction()
            .add(R.id.fragmentContainerAdmin, profileFragment)
            .hide(profileFragment)
            .commit()

        mBinding.bottomNavAdmin.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_stalls -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(stallsFragment)
                        .commit()
                    activeFragment = stallsFragment
                    true
                }
                R.id.item_units -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(unitsFragment)
                        .commit()
                    activeFragment = unitsFragment
                    true
                }
                R.id.item_notifications -> {
                    fragmentManager.beginTransaction()
                        .hide(activeFragment)
                        .show(notificationsFragment)
                        .commit()
                    activeFragment = notificationsFragment
                    true
                }
                R.id.item_profile_admin -> {
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