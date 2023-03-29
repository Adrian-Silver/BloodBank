package com.example.bloodbank2_0_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import com.example.bloodbank2_0_main.databinding.ActivityDashboardBinding
import com.example.bloodbank2_0_main.databinding.ActivityMainBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbToolBar)

                replaceFragment(HomeFragment())




        binding.bnvBottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_item -> replaceFragment(HomeFragment())
                R.id.donor_item -> replaceFragment(DonorFragment())
                R.id.recipient_item -> replaceFragment(RecipientFragment())
                R.id.profile_item -> replaceFragment(ProfileFragment())
                else -> {

                }
            }
            true
        }

//        binding.bnvBottomNav.setOnItemSelectedListener { menuItem ->
//
//            when (menuItem.itemId) {
//                R.id.home_item -> {
//                    replaceFragment(HomeFragment())
//                    return@setOnItemSelectedListener true
//                }
//                R.id.donor_item -> {
//                    replaceFragment(DonorFragment())
//                    return@setOnItemSelectedListener true
//
//                }
//                R.id.recipient_item -> {
//                    replaceFragment(RecipientFragment())
//                    return@setOnItemSelectedListener true
//
//                }
//                R.id.profile_item -> {
//                    replaceFragment(ProfileFragment())
//                    return@setOnItemSelectedListener true
//
//                }
//                else -> return@setOnItemSelectedListener false
//
//            }
//        }
//



    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain, fragment)
            addToBackStack(null)
            commit()

//        supportFragmentManager.beginTransaction()
//            .replace(binding.flMain.id, fragment)
//                .commit()

    }
}
}
