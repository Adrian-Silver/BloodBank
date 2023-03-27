package com.example.bloodbank2_0_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }

    fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
