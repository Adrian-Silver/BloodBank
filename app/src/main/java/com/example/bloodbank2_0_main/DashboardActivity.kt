package com.example.bloodbank2_0_main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.bloodbank2_0_main.databinding.ActivityDashboardBinding
import com.example.bloodbank2_0_main.databinding.ActivityMainBinding
import com.example.bloodbank2_0_main.mpesa.MpesaActivity2

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection

        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_payment -> {
                val intent = Intent(this, MpesaActivity2::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}
